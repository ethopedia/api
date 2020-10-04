package youtube

import java.time.OffsetDateTime

interface YoutubeVideoEventListener {

    fun onVideoUpload(videoData: UploadedYoutubeVideo)

    fun onVideoTitleUpdate(videoId: String, newTitle: String)

    fun onVideoDescriptionUpdate(videoId: String, newDescription: String)


    data class UploadedYoutubeVideo(
        val channelId: String,
        val videoId: String,
        val videoTitle: String,
        val videoDescription: String,
        // Timestamp marking when the video was uploaded
        val uploadTime: OffsetDateTime
    )
}