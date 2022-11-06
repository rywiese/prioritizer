package ry.prioritizer.ktor.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import ry.prioritizer.http.GetRootHandler
import ry.prioritizer.http.GetTreeHandler
import ry.prioritizer.ktor.KtorPlugin
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HttpPlugin @Inject constructor(
    private val getRootHandler: GetRootHandler,
    private val getTreeHandler: GetTreeHandler
) : KtorPlugin() {

    override fun Application.configure() {
        routing {
            route("tree") {
                get(getRootHandler)
                route("{categoryId}") {
                    get(getTreeHandler)
                }
            }
        }
    }

}
