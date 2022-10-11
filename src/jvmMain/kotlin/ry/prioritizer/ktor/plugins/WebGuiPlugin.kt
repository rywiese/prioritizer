package ry.prioritizer.ktor.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.script
import kotlinx.html.title
import ry.prioritizer.ktor.KtorPlugin
import javax.inject.Inject

class WebGuiPlugin @Inject constructor() : KtorPlugin() {

    override fun Application.configure() {
        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK) {
                    head {
                        title("Prioritizer")
                    }
                    body {
                        div {
                            id = "root"
                        }
                        script(src = "/static/prioritizer.js") {}
                    }
                }
            }
            static("/static") {
                resources()
            }
        }
    }

}
