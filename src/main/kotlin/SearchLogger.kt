import com.google.inject.Inject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.jdbi.v3.core.Jdbi
import java.sql.SQLException

interface SearchLogger {
    fun log(searchText: String)
}

class TerminalOutputSearchLogger : SearchLogger {
    override fun log(searchText: String) {
        println("Logging search: $searchText")
    }
}

class DatabaseSearchLogger @Inject constructor(private val jdbi: Jdbi) : SearchLogger {

    private fun submitSearch(searchText: String) {
        jdbi.useHandle<SQLException> { handle ->
            handle.createUpdate("insert into searches (query) values (?)")
                .bind(0, searchText)
                .execute()
        }
    }

    override fun log(searchText: String) {
        GlobalScope.async {
            submitSearch(searchText)
        }
    }
}