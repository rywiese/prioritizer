package ry.prioritizer.ktor.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import ry.prioritizer.http.CreateItemHandler
import ry.prioritizer.http.CreateSubcategoryHandler
import ry.prioritizer.http.DeleteCategoryHandler
import ry.prioritizer.http.DeleteItemHandler
import ry.prioritizer.http.GetRootHandler
import ry.prioritizer.http.GetTreeHandler
import ry.prioritizer.http.PopItemHandler
import ry.prioritizer.ktor.KtorPlugin
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestPlugin @Inject constructor(
    private val getRootHandler: GetRootHandler,
    private val getTreeHandler: GetTreeHandler,
    private val deleteCategoryHandler: DeleteCategoryHandler,
    private val createSubcategoryHandler: CreateSubcategoryHandler,
    private val createItemHandler: CreateItemHandler,
    private val popItemHandler: PopItemHandler,
    private val deleteItemHandler: DeleteItemHandler,
) : KtorPlugin() {

    override fun Application.configure() {
        routing {
            route("tree") {
                get(getRootHandler)
                route("{categoryId}") {
                    get(getTreeHandler)
                    delete(deleteCategoryHandler)
                    route("subcategories") {
                        post(createSubcategoryHandler)
                    }
                    route("items") {
                        post(createItemHandler)
                        patch(popItemHandler)
                    }
                }
            }
            route("items") {
                route("{itemId}") {
                    delete(deleteItemHandler)
                }
            }
        }
    }

}
