package dao

class DummyVideoSearchService: VideoSearchService {

    private val results = listOf(
        SearchResult(
            id = "SEARCH_RESULT_ID",
            videoId = "VIDEO_ID",
            episodeNumber = 0,
            videoName = "VIDEO_NAME",
            matchedText = "MATCHED_TEXT",
            startTime = 0
        )
    )

    override fun searchVideos(searchText: String, offset: Int, limit: Int): List<SearchResult> {
        return results
    }

    override fun getRandomVideoMatchingSearch(searchText: String): SearchResult? {
        return results.first()
    }
}