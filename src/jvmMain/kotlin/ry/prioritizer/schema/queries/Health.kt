package ry.prioritizer.schema.queries

import com.expediagroup.graphql.server.operations.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Health @Inject constructor() : Query {

    fun hello(): String = "healthy"

}
