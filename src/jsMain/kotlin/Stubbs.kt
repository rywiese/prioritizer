import model.DeepTree
import model.Item
import model.ShallowTree

val shallowBudget = ShallowTree(
    id = "newTree",
    name = "Budget",
    queue = emptyList(),
    childIds = setOf(
        "food",
        "clothes"
    )
)

val budget = DeepTree(
    id = "newTree",
    name = "Budget",
    queue = emptyList(),
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
            children = emptySet()
        ),
        DeepTree(
            id = "clothes",
            name = "Clothes",
            queue = emptyList(),
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
                    children = emptySet()
                )
            )
        )
    )
)
