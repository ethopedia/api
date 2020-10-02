import com.google.common.util.concurrent.RateLimiter
import io.javalin.http.Context

class ContextProvider: GraphQLContextProvider {

    private val rateLimiter = RateLimiter.create(1.0)

    override fun createContext(context: Context, token: String?, tokenFgp: String?): Any? {
        TODO()
    }
}