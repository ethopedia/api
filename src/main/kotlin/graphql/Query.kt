package graphql

import SearchLogger
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import com.google.inject.Inject
import dao.SearchResult
import dao.VideoSearchService
import graphql.schema.DataFetchingEnvironment
import org.jdbi.v3.core.Jdbi
import schemabuilder.annotations.graphql.GraphQLDataFetcher
import schemabuilder.annotations.graphql.GraphQLTypeConfiguration
import util.withService
import java.util.concurrent.TimeUnit


@GraphQLTypeConfiguration("Query")
class Query @Inject constructor(
    private val jdbi: Jdbi,
    private val searchLogger: SearchLogger
) {

    @GraphQLDataFetcher("ping")
    fun ping() = "pong"

    @GraphQLDataFetcher("searchVideos")
    fun searchVideos(env: DataFetchingEnvironment): List<SearchResult> {
        val query = env.getArgument<String>("searchText")
        val offset = env.getArgument<Int>("offset") ?: 0
        val limit = (env.getArgument<Int>("limit") ?: 0).let {
            if (it > 50) 50 else it
        }

        return jdbi.withService<VideoSearchService>().searchVideos(query, offset, limit).also {
            if (offset == 0) {
                searchLogger.submitSearch(query)
            }
        }
    }

    @GraphQLDataFetcher("getRandomVideoMatchingSearch")
    fun getRandomVideoMatchingSearch(env: DataFetchingEnvironment): SearchResult? {
        val query = env.getArgument<String>("searchText")

        return jdbi.withService<VideoSearchService>().getRandomVideoMatchingSearch(query).also {
            searchLogger.submitSearch(query)
        }
    }
}
