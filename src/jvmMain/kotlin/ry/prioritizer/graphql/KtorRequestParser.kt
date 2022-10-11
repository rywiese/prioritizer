package ry.prioritizer.graphql

import com.expediagroup.graphql.server.execution.GraphQLRequestParser
import com.expediagroup.graphql.server.types.GraphQLServerRequest
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.receiveText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorRequestParser @Inject constructor(
    private val objectMapper: ObjectMapper
) : GraphQLRequestParser<ApplicationRequest> {

    override suspend fun parseRequest(request: ApplicationRequest): GraphQLServerRequest? =
        objectMapper.readValue(request.call.receiveText(), GraphQLServerRequest::class.java)

}
