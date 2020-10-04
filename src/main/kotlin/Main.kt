import com.google.inject.Guice
import core.GraphQLScalars
import dev.misfitlabs.kotlinguice4.getInstance
import graphql.execution.instrumentation.ChainedInstrumentation
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentation
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentationOptions
import io.javalin.Javalin
import io.javalin.http.HandlerType
import schemabuilder.processor.GraphQLBuilder
import schemabuilder.processor.wiring.InstanceFetcher
import youtube.YoutubeWebhookHandler
import youtube.YoutubeWebhookModule
import kotlin.reflect.KClass

fun main() {
    val injector = Guice.createInjector(
        PostgresModule(),
        YoutubeWebhookModule()
    )

    val fetcher = object : InstanceFetcher {
        override fun <T : Any> getInstance(clazz: KClass<T>): T {
            return injector.getInstance(clazz.java)
        }
    }

    val gql = GraphQLBuilder.newGraphQLBuilder()
        .setBasePackageForClasses("graphql")
        .setSchemaFileExtension("graphqls")
        .addClass(GraphQLScalars.DATE_TIME)
        .addClass(GraphQLScalars.DATE)
        .addClass(GraphQLScalars.UUID)
        .setInstrumentation(
            ChainedInstrumentation(
                listOf(
                    // TracingInstrumentation(),
                    DataLoaderDispatcherInstrumentation(
                        DataLoaderDispatcherInstrumentationOptions.newOptions().includeStatistics(false)
                    )
                )
            )
        )
        .setMaxQueryCost(500)
        .setInstanceFetcher(fetcher)
        .build()
        .generateGraphQL()


    val port = 7250
    val props = JavalinProperties(
        port,
        "/graphql",
        "*",
        listOf("Content-Type", "Authorization"),
        emptyList()
    )

    Javalin.create {
        it.registerPlugin(GraphQLPlugin(props.endpoint, gql, null, false))
    }.before {
        it.res.contentType = "application/json"
        it.res.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS")
        it.res.addHeader("Access-Control-Allow-Origin", props.originHeader)
        it.res.addHeader("Access-Control-Allow-Headers", props.allowedHeaders.joinToString(","))
    }
        .addHandler(HandlerType.OPTIONS, "*") {}
        .addHandler(HandlerType.GET, "/ping") { it.result("pong") }
        .addHandler(HandlerType.POST, "/youtube", injector.getInstance<YoutubeWebhookHandler>())
        .start(props.port)

    println("API running on port $port")
}