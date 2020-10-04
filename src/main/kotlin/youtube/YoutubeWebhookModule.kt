package youtube

import dev.misfitlabs.kotlinguice4.KotlinModule

class YoutubeWebhookModule: KotlinModule() {

    override fun configure() {
        bind<YoutubeVideoEventListener>().to<DefaultYoutubeVideoEventListener>()
    }
}
