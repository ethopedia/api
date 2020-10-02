import com.google.inject.Inject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.jdbi.v3.core.Jdbi
import java.sql.SQLException

class SearchLogger @Inject constructor(private val jdbi: Jdbi) {

    fun submitSearch(searchText: String) {
        GlobalScope.async {
            logSearch(searchText)
        }
    }

    private fun logSearch(searchText: String) {
        jdbi.useHandle<SQLException> { handle ->
            handle.createUpdate("insert into searches (query) values (?)")
                .bind(0, searchText)
                .execute()
        }
    }
}