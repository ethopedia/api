package youtube

import com.google.inject.Inject
import io.javalin.http.Context
import io.javalin.http.Handler

/**
 *  A Javalin handler that handles callbacks from YouTube.
 *  https://developers.google.com/youtube/v3/guides/push_notifications
 *
 *  This class needs to handle the following three cases:
 *      1. A new video is uploaded
 *      2. An existing video's title is updated
 *      3. An existing video's description is updated
 */
class YoutubeWebhookHandler @Inject constructor(
    private val videoEventListener: YoutubeVideoEventListener
) : Handler {

    override fun handle(ctx: Context) {
        TODO("Not yet implemented")
    }
}
