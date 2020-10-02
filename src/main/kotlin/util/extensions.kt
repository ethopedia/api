package util

import org.jdbi.v3.core.Jdbi

inline fun <reified DAO : Any> Jdbi.withService(): DAO {
    return onDemand(DAO::class.java)
}