package client

import api.PrioritizerApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import model.Category
import model.Item
import model.Tree

object HttpPrioritizerClient : PrioritizerApi {

    private val client = HttpClient(Js) {
        install(ContentNegotiation) {
            json(Json)
        }
    }

    override suspend fun getRoot(
        maxDepth: Int
    ): Tree? =
        client.get("http://localhost:8080/tree?maxDepth=$maxDepth")
            .takeIf { httpResponse: HttpResponse ->
                httpResponse.status == HttpStatusCode.OK
            }
            ?.body<JsonElement>()
            ?.let { jsonElement: JsonElement ->
                Tree.fromJson(jsonElement)
            }

    override suspend fun getTree(
        categoryId: String,
        maxDepth: Int
    ): Tree? =
        client.get("http://localhost:8080/tree/$categoryId?maxDepth=$maxDepth")
            .takeIf { httpResponse: HttpResponse ->
                httpResponse.status == HttpStatusCode.OK
            }
            ?.body<JsonElement>()
            ?.let { jsonElement: JsonElement ->
                Tree.fromJson(jsonElement)
            }

    override suspend fun createCategory(parentId: String, name: String): Category? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategory(categoryId: String): String? {
        TODO("Not yet implemented")
    }

    override suspend fun createItem(categoryId: String, name: String, price: Double, link: String): Item? {
        TODO("Not yet implemented")
    }

    override suspend fun popItem(categoryId: String): Item? {
        TODO("Not yet implemented")
    }

}
