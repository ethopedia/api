package dao

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator
import org.jdbi.v3.sqlobject.statement.SqlQuery

data class SearchResult(
    val videoId: String,
    val id: String,
    val episodeNumber: Int,
    val videoName: String,
    val matchedText: String,
    val startTime: Int? = null
)

@UseClasspathSqlLocator
@RegisterKotlinMapper(SearchResult::class)
interface VideoSearchService {

    @SqlQuery
    fun searchVideos(
        @Bind("searchText") searchText: String,
        @Bind("offset") offset: Int = 25,
        @Bind("limit") limit: Int = 0
    ): List<SearchResult>

    @SqlQuery
    fun getRandomVideoMatchingSearch(
        @Bind("searchText") searchText: String
    ): SearchResult?
}