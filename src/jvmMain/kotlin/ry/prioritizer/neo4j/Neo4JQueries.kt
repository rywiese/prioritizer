package ry.prioritizer.neo4j

import io.ktor.server.routing.RoutingPath.Companion.root
import org.neo4j.driver.Record
import org.neo4j.driver.Transaction
import org.neo4j.driver.types.Node
import ry.prioritizer.schema.model.Category
import ry.prioritizer.schema.model.Item
import ry.prioritizer.schema.model.Tree

object Neo4JQueries {

    fun Transaction.getCategory(
        categoryId: String
    ): Category? =
        run("match (category:Category) where ID(category)=$categoryId return category")
            .list()
            .firstOrNull()
            ?.get("category")
            ?.asNode()
            ?.let { root: Node ->
                Category(
                    id = root.id().toString(),
                    name = root["name"].asString()
                )
            }

    fun Transaction.getQueue(
        categoryId: String
    ): List<Item>? =
        run("match (category:Category)-[:NEXT_ITEM*0..1]->(item) where ID(category)=$categoryId return category, ID(item) as itemId")
            .list()
            .let { records: List<Record> ->
                val category: Category? = records
                    .getOrNull(0)
                    ?.get("category")
                    ?.asNode()
                    ?.let { root: Node ->
                        Category(
                            id = root.id().toString(),
                            name = root["name"].asString()
                        )
                    }
                val itemId: String? = records
                    .getOrNull(1)
                    ?.get("itemId")
                    ?.asLong()
                    ?.toString()
                category?.to(itemId)
            }
            ?.let { (_, itemId: String?) ->
                itemId?.let { getQueueStartingWith(it) } ?: emptyList()
            }

    fun Transaction.getQueueStartingWith(
        itemId: String
    ): List<Item>? =
        run("match (item:Item)-[PRECEDES*0..1]->(nextItem:Item) where ID(item)=$itemId return item, ID(nextItem) as nextItemId")
            .list()
            .let { records: List<Record> ->
                val item: Item? = records
                    .getOrNull(0)
                    ?.get("item")
                    ?.asNode()
                    ?.let { node: Node ->
                        Item(
                            id = node.id().toString(),
                            name = node["name"].asString(),
                            price = node["price"].asDouble(),
                            link = node["link"].asString()
                        )
                    }
                val nextItemId: String? = records
                    .getOrNull(1)
                    ?.get("nextItemId")
                    ?.asLong()
                    ?.toString()
                item?.to(nextItemId)
            }
            ?.let { (item: Item, nextItemId: String?) ->
                listOf(item) + (nextItemId?.let { getQueueStartingWith(it) } ?: emptyList())
            }

    fun Transaction.getChildIds(
        categoryId: String
    ): Set<String>? =
        run("match (category:Category) -[:PARENT_OF]-> (child) where ID(category)=$categoryId return category, child")
            .list { record: Record ->
                record["child"].asNode().id().toString()
            }
            .toSet()

    fun Transaction.getRoot(): Tree? =
        run("match (root) -[:PARENT_OF*0..]-> (child) where not ()-[:PARENT_OF]->(root) return root")
            .list()
            .firstOrNull()
            ?.get("root")
            ?.asNode()
            ?.let { root: Node ->
                Category(
                    id = root.id().toString(),
                    name = root["name"].asString()
                )
            }
            ?.let { category: Category ->
                Tree(
                    category,
                    queue = getQueue(category.id)!!,
                    children = getChildIds(category.id)!!.map { childId: String ->
                        getTree(childId)!!
                    }
                )
            }

    fun Transaction.getTree(
        categoryId: String
    ): Tree? =
        getCategory(categoryId)?.let { category: Category ->
            Tree(
                category = category,
                queue = getQueue(category.id)!!,
                children = getChildIds(category.id)!!.map { childId: String ->
                    getTree(childId)!!
                }
            )
        }

    fun Transaction.deleteCategory(
        categoryId: String
    ) {
        run("match (category:Category)-[*0..]->(child) where ID(category)=$categoryId detach delete child")
    }

}
