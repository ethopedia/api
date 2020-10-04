package graphql

import SearchLogger
import com.google.inject.Inject
import dao.SearchResult
import dao.VideoSearchService
import graphql.schema.DataFetchingEnvironment
import schemabuilder.annotations.graphql.GraphQLDataFetcher
import schemabuilder.annotations.graphql.GraphQLTypeConfiguration


@GraphQLTypeConfiguration("Query")
class Query @Inject constructor(
    private val videoSearchService: VideoSearchService,
    private val searchLogger: SearchLogger
) {

    @GraphQLDataFetcher("ping")
    fun ping() = "pong"

    @GraphQLDataFetcher("searchVideos")
    fun searchVideos(env: DataFetchingEnvironment): List<SearchResult> {
        val query = env.getArgument<String>("searchText")
        val offset = env.getArgument<Int>("offset") ?: 0
        val limit = (env.getArgument<Int>("limit") ?: 25).coerceAtMost(50)

        return videoSearchService.searchVideos(query, offset, limit).also {
            if (offset == 0) {
                searchLogger.log(query)
            }
        }
    }

    @GraphQLDataFetcher("getRandomVideoMatchingSearch")
    fun getRandomVideoMatchingSearch(env: DataFetchingEnvironment): SearchResult? {
        val query = env.getArgument<String>("searchText")

        return videoSearchService.getRandomVideoMatchingSearch(query).also {
            searchLogger.log(query)
        }
    }
}
