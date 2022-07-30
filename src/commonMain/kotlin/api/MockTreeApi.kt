package api

import model.DeepTree
import model.Item
import model.Tree

class MockTreeApi : TreeApi {

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

    val budget = DeepTree(
        id = "newTree",
        name = "Budget",
        queue = emptyList(),
        parentId = null,
        children = setOf(
            DeepTree(
                id = "food",
                name = "Food",
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
                parentId = "newTree",
                children = emptySet()
            ),
            DeepTree(
                id = "clothes",
                name = "Clothes",
                queue = emptyList(),
                parentId = "newTree",
                children = setOf(
                    DeepTree(
                        id = "shoes",
                        name = "Shoes",
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
                    DeepTree(
                        id = "socks",
                        name = "Socks",
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
