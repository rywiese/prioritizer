package api

import model.Category
import model.Item
import model.Tree

object MockTreeApi : TreeApi {

    override suspend fun getRoot(
        treeDepth: Int,
        queueLength: Int
    ): Tree? =
        getTree(
            treeId = budget.id,
            treeDepth = treeDepth,
            queueLength = queueLength
        )

    override suspend fun getTree(
        treeId: String,
        treeDepth: Int,
        queueLength: Int
    ): Tree? =
        budget
            .subTree(treeId)
            ?.limitDepth(treeDepth)

    override suspend fun getParent(
        treeId: String,
        treeDepth: Int,
        queueLength: Int
    ): Tree? =
        getParent(
            root = budget,
            treeId = treeId
        )

    private fun getParent(
        root: Tree,
        treeId: String,
    ): Tree? =
        root.children
            .find { tree: Tree ->
                tree.id == treeId
            }
            ?.let {
                root
            }
            ?: root.children.firstNotNullOfOrNull { tree: Tree ->
                getParent(
                    root = tree,
                    treeId = treeId
                )
            }

    val budget = Tree(
        Category(
            id = "budget",
            name = "Budget",
        ),
        queue = emptyList(),
        parentId = null,
        children = setOf(
            Tree(
                Category(
                    id = "food",
                    name = "Food",
                ),
                queue = listOf(
                    Item(
                        id = "huel",
                        name = "Huel",
                        price = 20.0,
                        link = "https://huel.com"
                    ),
                    Item(
                        id = "coconutMilk",
                        name = "Coconut Milk",
                        price = 1.29,
                        link = "https://cub.com/coconutmilk"
                    ),
                    Item(
                        id = "rice",
                        name = "Rice",
                        price = .97,
                        link = "https://cub.com/rice"
                    )
                ),
                parentId = "budget",
                children = emptySet()
            ),
            Tree(
                Category(
                    id = "clothes",
                    name = "Clothes",
                ),
                queue = emptyList(),
                parentId = "budget",
                children = setOf(
                    Tree(
                        Category(
                            id = "shoes",
                            name = "Shoes",
                        ),
                        queue = listOf(
                            Item(
                                id = "flipFlops",
                                name = "Flip Flops",
                                price = 10.0,
                                link = "https://target.com/flipflops"
                            ),
                            Item(
                                id = "vans",
                                name = "Vans",
                                price = 50.0,
                                link = "https://journeys.com/vans"
                            ),
                            Item(
                                id = "rudyGiulianis",
                                name = "Rudy Giuilianis",
                                price = 49.95,
                                link = "https://mypillow.com/rudygs"
                            )
                        ),
                        parentId = "clothes",
                        children = emptySet()
                    ),
                    Tree(
                        Category(
                            id = "socks",
                            name = "Socks",
                        ),
                        queue = listOf(
                            Item(
                                id = "adidas",
                                name = "Adidas",
                                price = 15.0,
                                link = "https://adidas.com/socks"
                            ),
                            Item(
                                id = "nike",
                                name = "Nike",
                                price = 20.0,
                                link = "https://nike.com/socks"
                            )
                        ),
                        parentId = "clothes",
                        children = emptySet()
                    )
                )
            )
        )
    )

}
