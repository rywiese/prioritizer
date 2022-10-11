package ry.prioritizer.schema.queries

import com.expediagroup.graphql.server.operations.Query

object Health : Query {

    fun hello(): String = "healthy"

}
