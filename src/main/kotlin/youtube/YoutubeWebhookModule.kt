package youtube

import dev.misfitlabs.kotlinguice4.KotlinModule

/**
 * Guice module with bindings for all YouTube related classes
 */
class YoutubeWebhookModule: KotlinModule() {

    override fun configure() {
        bind<YoutubeVideoEventListener>().to<DefaultYoutubeVideoEventListener>()
    }
}
