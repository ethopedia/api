import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dao.DummyVideoSearchService
import dao.VideoSearchService
import dev.misfitlabs.kotlinguice4.KotlinModule
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import util.withService
import java.net.URI
import java.net.URISyntaxException
import kotlin.system.exitProcess

class PostgresClient {
    private val config = HikariConfig()

    private var connectionPool: HikariDataSource? = null

    var jdbi: Jdbi

    init {

        val host = System.getenv("PG_HOST")
        val port = System.getenv("PG_PORT")
        val user = System.getenv("PG_USER")
        val password = System.getenv("PG_PASSWORD")

        val dbUrl = String.format(
            "jdbc:postgresql://%s:%s/%s?user=%s&password=%s",
            host,
            port,
            "postgres",
            user,
            password
        )

        val dbUri: URI = try {
            URI(dbUrl)
        } catch (e: URISyntaxException) {
            // Kill the program
            exitProcess(1)
        }

        if (dbUri.userInfo != null) {
            config.username = dbUri.userInfo.split(":").toTypedArray()[0]
            config.password = dbUri.userInfo.split(":").toTypedArray()[1]
        }

        config.jdbcUrl = dbUrl
        config.poolName = "Java App"
        config.maximumPoolSize = 2
        config.addDataSourceProperty("cachePrepStmts", "true")
        config.addDataSourceProperty("prepStmtCacheSize", "250")
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        config.addDataSourceProperty("ApplicationName", "Java App")

        connectionPool = HikariDataSource(config)

        // Close the pool on shutdown
        Runtime.getRuntime().addShutdownHook(Thread { connectionPool!!.close() })
        jdbi = Jdbi.create(connectionPool)

        // Install plugins
        jdbi.installPlugin(PostgresPlugin())
        jdbi.installPlugin(SqlObjectPlugin())
        jdbi.installPlugin(KotlinPlugin())
        jdbi.installPlugin(KotlinSqlObjectPlugin())
    }
}

class PostgresModule(
    private val programArgs: Array<String>
) : KotlinModule() {
    override fun configure() {
        if (programArgs.contains("--no-database")) {
            bind<SearchLogger>().to<TerminalOutputSearchLogger>()
            bind<VideoSearchService>().to<DummyVideoSearchService>()
        } else {
            val jdbi = PostgresClient().jdbi

            bind<Jdbi>().toInstance(jdbi)
            bind<SearchLogger>().to<DatabaseSearchLogger>()
            bind<VideoSearchService>().toInstance(jdbi.withService())
        }
    }
}