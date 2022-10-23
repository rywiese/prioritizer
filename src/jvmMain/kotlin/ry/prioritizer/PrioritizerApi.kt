package ry.prioritizer

import ry.prioritizer.schema.model.Category
import ry.prioritizer.schema.model.Item
import ry.prioritizer.schema.model.Tree

interface PrioritizerApi {

    fun getRoot(): Tree?

    fun getTree(categoryId: String): Tree?

    fun createCategory(parentId: String, name: String): Category?

    /**
     * TODO: Return the deleted [Category.id], or `null` if the [Category] was not found.
     */
    fun deleteCategory(categoryId: String): String?

    fun createItem(categoryId: String, name: String, price: Double, link: String): Item?

    fun popItem(categoryId: String): Item?

}
