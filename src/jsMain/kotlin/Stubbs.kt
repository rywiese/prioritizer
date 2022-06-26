import model.DeepTree
import model.Item
import model.Node

val item1 = Item(
    id = "item::1",
    name = "Item 1",
    price = 3.98,
    link = "http://amazon.com/item1"
)

val item2 = Item(
    id = "item::2",
    name = "Item 2",
    price = 43.45,
    link = "http://target.com/item2"
)

val item3 = Item(
    id = "item::3",
    name = "Item 3",
    price = 948.21,
    link = "http://bestbuy.com/item3"
)

val item4 = Item(
    id = "item::4",
    name = "Item 4",
    price = 4.12,
    link = "http://amazon.com/item4"
)

val item5 = Item(
    id = "item::5",
    name = "Item 5",
    price = 16.73,
    link = "http://target.com/item5"
)

val item6 = Item(
    id = "item::6",
    name = "Item 6",
    price = 85.21,
    link = "http://bestbuy.com/item6"
)

val item7 = Item(
    id = "item::7",
    name = "Item 7",
    price = 16.73,
    link = "http://target.com/item7"
)

val item8 = Item(
    id = "item::8",
    name = "Item 8",
    price = 85.21,
    link = "http://bestbuy.com/item8"
)

val tree1 = DeepTree(
    id = "tree::1",
    node = Node(
        id = "node::1",
        name = "Tree 1"
    ),
    queue = listOf(
        item1,
        item2,
        item3,
        item4
    ),
    children = emptySet()
)

val tree2 = DeepTree(
    id = "tree::2",
    node = Node(
        id = "node::2",
        name = "Tree 2"
    ),
    queue = emptyList(),
    children = emptySet()
)

val tree3 = DeepTree(
    id = "tree::3",
    node = Node(
        id = "node::3",
        name = "Tree 3"
    ),
    queue = listOf(
        item5,
        item6
    ),
    children = emptySet()
)

val tree4 = DeepTree(
    id = "tree::4",
    node = Node(
        id = "node::4",
        name = "Tree 4"
    ),
    queue = listOf(
        item7,
        item8
    ),
    children = setOf(
        tree1,
        tree2,
        tree3
    )
)
