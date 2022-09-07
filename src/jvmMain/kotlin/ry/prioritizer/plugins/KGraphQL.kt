package ry.prioritizer.plugins

import api.MockTreeApi
import com.apurebase.kgraphql.GraphQL
import io.ktor.application.Application
import io.ktor.application.install
import model.Item
import model.Tree

fun Application.configureKGraphQL() {
    install(GraphQL) {
        playground = true
        schema {
            configure {
                useDefaultPrettyPrinter = true
            }
            query("hello") {
                resolver { ->
                    "world"
                }
            }
            query("root") {
                resolver { depth: Int ->
                    MockTreeApi.budget.limitDepth(depth)
                }
            }
            type<Tree>()
            type<Item>()
        }
    }
}
