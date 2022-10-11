package ry.prioritizer.ktor.plugins

import com.expediagroup.graphql.generator.extensions.print
import com.expediagroup.graphql.server.execution.GraphQLServer
import com.expediagroup.graphql.server.types.GraphQLServerResponse
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.GraphQLSchema
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import ry.prioritizer.ktor.KtorPlugin
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class GraphQLPlugin @Inject constructor(
    private val graphQLSchema: GraphQLSchema,
    private val graphQLServer: GraphQLServer<ApplicationRequest>,
    @Named("playgroundHtml") private val playgroundHtml: String,
    private val objectMapper: ObjectMapper
) : KtorPlugin() {

    override fun Application.configure() {
        routing {
            get("sdl") {
                call.respondText(graphQLSchema.print())
            }
            post("graphql") {
                val response: GraphQLServerResponse? = graphQLServer.execute(call.request)
                if (response != null) {
                    call.respond(objectMapper.writeValueAsString(response))
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
            get("playground") {
                call.respondText(playgroundHtml, ContentType.Text.Html)
            }
        }
    }

}
