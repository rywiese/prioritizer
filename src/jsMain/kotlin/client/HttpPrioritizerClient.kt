package client

import api.PrioritizerApi
import http.CreateItemRequest
import http.CreateSubcategoryRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import ktor.bodyFromJson
import ktor.setJsonBody
import model.Category
import model.Item
import model.Tree
import serialization.CategorySerializer
import serialization.ComposedJsonArraySerializer
import serialization.CreateItemRequestSerializer
import serialization.CreateSubcategoryRequestSerializer
import serialization.ItemSerializer
import serialization.JsonSerializer
import serialization.TreeSerializer

// TODO:
//  1. DI
//  2. Better exception handling
object HttpPrioritizerClient : PrioritizerApi {

    private val apiUrl: String = "http://localhost:8080"
    private val client = HttpClient(Js) {
        install(ContentNegotiation) {
            json(Json)
        }
    }
    private val categorySerializer: JsonSerializer<Category> = CategorySerializer
    private val itemSerializer: JsonSerializer<Item> = ItemSerializer
    private val queueSerializer: JsonSerializer<List<Item>> = ComposedJsonArraySerializer(itemSerializer)
    private val treeSerializer: JsonSerializer<Tree> = TreeSerializer(categorySerializer, queueSerializer)
    private val createSubcategoryRequestSerializer: JsonSerializer<CreateSubcategoryRequest> =
        CreateSubcategoryRequestSerializer
    private val createItemRequestSerializer: JsonSerializer<CreateItemRequest> = CreateItemRequestSerializer

    override suspend fun getRoot(
        maxDepth: Int
    ): Tree? =
        client.get("$apiUrl/tree?maxDepth=$maxDepth")
            .takeIf { httpResponse: HttpResponse ->
                httpResponse.status == HttpStatusCode.OK
            }
            ?.bodyFromJson(treeSerializer)

    override suspend fun getTree(
        categoryId: String,
        maxDepth: Int
    ): Tree? =
        client.get("$apiUrl/tree/$categoryId?maxDepth=$maxDepth")
            .takeIf { httpResponse: HttpResponse ->
                httpResponse.status == HttpStatusCode.OK
            }
            ?.bodyFromJson(treeSerializer)

    override suspend fun createSubcategory(
        parentId: String,
        name: String
    ): Category? =
        createSubcategory(
            parentId,
            CreateSubcategoryRequest(name)
        )

    suspend fun createSubcategory(
        parentId: String,
        createSubcategoryRequest: CreateSubcategoryRequest
    ): Category? =
        client
            .post("$apiUrl/tree/$parentId/subcategory") {
                setJsonBody(
                    body = createSubcategoryRequest,
                    createSubcategoryRequestSerializer
                )
            }
            .takeIf { httpResponse: HttpResponse ->
                httpResponse.status == HttpStatusCode.OK
            }
            ?.bodyFromJson(categorySerializer)

    override suspend fun deleteCategory(
        categoryId: String
    ): String? =
        client.delete("$apiUrl/tree/$categoryId")
            .takeIf { httpResponse: HttpResponse ->
                httpResponse.status == HttpStatusCode.OK
            }
            ?.bodyFromJson(categorySerializer)
            ?.id

    override suspend fun createItem(
        categoryId: String,
        createItemRequest: CreateItemRequest
    ): Item? =
        client
            .post("$apiUrl/tree/$categoryId/items") {
                setJsonBody(createItemRequest, createItemRequestSerializer)
            }
            .takeIf { httpResponse: HttpResponse ->
                httpResponse.status == HttpStatusCode.OK
            }
            ?.bodyFromJson(itemSerializer)

    override suspend fun popItem(
        categoryId: String
    ): Item? =
        client.patch("$apiUrl/tree/$categoryId/items")
            .takeIf { httpResponse: HttpResponse ->
                httpResponse.status == HttpStatusCode.OK
            }
            ?.let { httpResponse: HttpResponse ->
                httpResponse.body<JsonObject>()
            }
            ?.takeIf(JsonObject::isNotEmpty)
            ?.let(itemSerializer::fromJson)

}
