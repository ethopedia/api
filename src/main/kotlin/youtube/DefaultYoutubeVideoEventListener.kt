package youtube

/**
 * Handles persisting any video updates to the database
 */
class DefaultYoutubeVideoEventListener : YoutubeVideoEventListener {
    override fun onVideoUpload(videoData: YoutubeVideoEventListener.UploadedYoutubeVideo) {
        TODO("Not yet implemented")
    }

    override fun onVideoTitleUpdate(videoId: String, newTitle: String) {
        TODO("Not yet implemented")
    }

    override fun onVideoDescriptionUpdate(videoId: String, newDescription: String) {
        TODO("Not yet implemented")
    }
}
