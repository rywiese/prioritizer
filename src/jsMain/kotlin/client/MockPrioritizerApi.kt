package client

import api.PrioritizerApi
import model.Category
import model.Item
import model.Tree

object MockPrioritizerApi : PrioritizerApi {

    override suspend fun getRoot(): Tree = budget

    override suspend fun getTree(categoryId: String): Tree? = budget.getSubtree(categoryId)

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

    private data class ItemImpl(
        override val id: String,
        override val name: String,
        override val price: Double,
        override val link: String
    ) : Item

    private data class CategoryImpl(
        override val id: String,
        override val name: String
    ) : Category

    private data class TreeImpl(
        override val category: Category,
        override val queue: List<Item>,
        override val children: List<TreeImpl>
    ) : Tree {

        fun getSubtree(categoryId: String): Tree? =
            takeIf { category.id == categoryId } ?: children.firstNotNullOfOrNull { child: TreeImpl ->
                child.getSubtree(categoryId)
            }

    }

    private val budget = TreeImpl(
        category = CategoryImpl(
            id = "newTree",
            name = "Budget"
        ),
        queue = emptyList(),
        children = listOf(
            TreeImpl(
                category = CategoryImpl(
                    id = "food",
                    name = "Food"
                ),
                queue = listOf(
                    ItemImpl(
                        id = "huel",
                        name = "Huel",
                        price = 20.0,
                        link = "https://huel.com"
                    ),
                    ItemImpl(
                        id = "coconutMilk",
                        name = "Coconut Milk",
                        price = 1.29,
                        link = "https://cub.com/coconutmilk"
                    ),
                    ItemImpl(
                        id = "rice",
                        name = "Rice",
                        price = .97,
                        link = "https://cub.com/rice"
                    )
                ),
                children = emptyList()
            ),
            TreeImpl(
                category = CategoryImpl(
                    id = "clothes",
                    name = "Clothes"
                ),
                queue = emptyList(),
                children = listOf(
                    TreeImpl(
                        category = CategoryImpl(
                            id = "shoes",
                            name = "Shoes"
                        ),
                        queue = listOf(
                            ItemImpl(
                                id = "flipFlops",
                                name = "Flip Flops",
                                price = 10.0,
                                link = "https://target.com/flipflops"
                            ),
                            ItemImpl(
                                id = "vans",
                                name = "Vans",
                                price = 50.0,
                                link = "https://journeys.com/vans"
                            ),
                            ItemImpl(
                                id = "rudyGiulianis",
                                name = "Rudy Giuilianis",
                                price = 49.95,
                                link = "https://mypillow.com/rudygs"
                            )
                        ),
                        children = emptyList()
                    ),
                    TreeImpl(
                        category = CategoryImpl(
                            id = "socks",
                            name = "Socks"
                        ),
                        queue = listOf(
                            ItemImpl(
                                id = "adidas",
                                name = "Adidas",
                                price = 15.0,
                                link = "https://adidas.com/socks"
                            ),
                            ItemImpl(
                                id = "nike",
                                name = "Nike",
                                price = 20.0,
                                link = "https://nike.com/socks"
                            )
                        ),
                        children = emptyList()
                    )
                )
            )
        )
    )

}
