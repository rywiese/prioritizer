package api

import http.CreateItemRequest
import model.Category
import model.Item
import model.Tree

interface PrioritizerApi {

    suspend fun getRoot(maxDepth: Int): Tree?

    suspend fun getTree(categoryId: String, maxDepth: Int): Tree?

    suspend fun createSubcategory(parentId: String, name: String): Category?

    suspend fun deleteCategory(categoryId: String): String?

    suspend fun createItem(categoryId: String, createItemRequest: CreateItemRequest): Item?

    // TODO: distinguish between missing category vs empty queue
    suspend fun popItem(categoryId: String): Item?

    suspend fun deleteItem(itemId: String): Item?

}
