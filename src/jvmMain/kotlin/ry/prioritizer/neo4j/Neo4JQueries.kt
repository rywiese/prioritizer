package ry.prioritizer.neo4j

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.neo4j.driver.Record
import org.neo4j.driver.Transaction
import org.neo4j.driver.async.AsyncTransaction

internal object Neo4JQueries {

    suspend fun AsyncTransaction.getCategory(
        categoryId: String
    ): Neo4JCategory? =
        runList("match (category:Category) where ID(category)=$categoryId return category")
            .firstOrNull()
            ?.get("category")
            ?.let(CategoryParser::parse)

    fun Transaction.createCategory(
        parentId: String,
        name: String
    ): Neo4JCategory? =
        run("match (parent) where ID(parent)=$parentId create (parent)-[:PARENT_OF]->(new:Category{name:\"$name\"}) return new")
            .list()
            .firstOrNull()
            ?.get("new")
            ?.let(CategoryParser::parse)

    fun Transaction.deleteCategory(
        categoryId: String
    ) {
        getChildIds(categoryId)
            ?.also {
                run("match (category:Category)-[*0..]->(child) where ID(category)=$categoryId detach delete child")
            }
            ?.map { childId: String ->
                deleteCategory(childId)
            }
    }

    fun Transaction.createItem(
        categoryId: String,
        name: String,
        price: Double,
        link: String
    ): Neo4JItem? =
        run("match (category:Category)-[:NEXT_ITEM*0..]->(item) where ID(category)=$categoryId and NOT (item)-[:NEXT_ITEM]->() create (item)-[:NEXT_ITEM]->(new:Item{name:\"$name\",price:$price,link:\"$link\"}) return new")
            .list()
            .firstOrNull()
            ?.get("new")
            ?.let(ItemParser::parse)

    fun Transaction.popItem(
        categoryId: String
    ): Neo4JItem? =
        run("match path=(category:Category)-[:NEXT_ITEM*1..2]->(item:Item) where ID(category)=$categoryId return item, LENGTH(path) as length")
            .list { record: Record ->
                val numberOfItems: Int = record["length"].asInt()
                val item: Neo4JItem = ItemParser.parse(record["item"])
                numberOfItems to item
            }
            .sortedBy { (numberOfItems: Int, _) ->
                numberOfItems
            }
            .map { (_, item: Neo4JItem) ->
                item
            }
            .let { items: List<Neo4JItem> ->
                items.getOrNull(0) to items.getOrNull(1)
            }
            .let { (itemToPop: Neo4JItem?, nextItem: Neo4JItem?) ->
                itemToPop?.also {
                    if (nextItem == null) {
                        run("match (category:Category)-[:NEXT_ITEM]->(item:Item) where ID(item)=${itemToPop.id} detach delete item")
                    } else {
                        run("match (category:Category)-[:NEXT_ITEM]->(item:Item)-[:NEXT_ITEM]->(nextItem:Item) where ID(item)=${itemToPop.id} and ID(nextItem)=${nextItem.id} detach delete item create (category)-[:NEXT_ITEM]->(nextItem)")
                    }
                }
            }

    suspend fun AsyncTransaction.getQueue(
        categoryId: String
    ): List<Neo4JItem>? =
        runList("match path=(category:Category)-[:NEXT_ITEM*0..]->(item) where ID(category)=$categoryId return item, LENGTH(path) as length")
            .takeIf(List<*>::isNotEmpty)
            ?.drop(1)
            ?.map { record: Record ->
                val numberOfItems: Int = record["length"].asInt()
                val item: Neo4JItem = ItemParser.parse(record["item"])
                numberOfItems to item
            }
            ?.sortedBy { (numberOfItems: Int, _) ->
                numberOfItems
            }
            ?.map { (_, item: Neo4JItem) ->
                item
            }

    suspend fun AsyncTransaction.getRoot(
        maxDepth: Int
    ): Neo4JTree? =
        runList("match (root:Category) where not ()-[:PARENT_OF]->(root) return root")
            .firstOrNull()
            ?.get("root")
            ?.let(CategoryParser::parse)
            ?.let { category: Neo4JCategory ->
                coroutineScope {
                    val queue: Deferred<List<Neo4JItem>> = async {
                        getQueue(category.id)!!
                    }
                    val children: Deferred<Set<Neo4JTree>> = async {
                        if (maxDepth <= 0) emptySet()
                        else getChildIds(category.id)!!
                            .map { childId: String ->
                                async {
                                    getTree(childId, maxDepth - 1)!!
                                }
                            }
                            .map { deferred: Deferred<Neo4JTree> ->
                                deferred.await()
                            }
                            .toSet()
                    }
                    Neo4JTree(
                        category,
                        queue = queue.await(),
                        children = children.await()
                    )
                }
            }

    suspend fun AsyncTransaction.getTree(
        categoryId: String,
        maxDepth: Int
    ): Neo4JTree? =
        getCategory(categoryId)?.let { category: Neo4JCategory ->
            coroutineScope {
                val queue: Deferred<List<Neo4JItem>> = async {
                    getQueue(categoryId)!!
                }
                val children: Deferred<Set<Neo4JTree>> = async {
                    if (maxDepth <= 0) emptySet()
                    else getChildIds(category.id)!!
                        .map { childId: String ->
                            async {
                                getTree(childId, maxDepth - 1)!!
                            }
                        }
                        .map { deferred: Deferred<Neo4JTree> ->
                            deferred.await()
                        }
                        .toSet()
                }
                Neo4JTree(
                    category,
                    queue = queue.await(),
                    children = children.await()
                )
            }
        }

    suspend fun AsyncTransaction.getChildIds(
        categoryId: String
    ): Set<String>? =
        runList("match (category:Category) -[:PARENT_OF]-> (child) where ID(category)=$categoryId return category, child")
            .map { record: Record ->
                record["child"].asNode().id().toString()
            }
            .toSet()

    fun Transaction.getChildIds(
        categoryId: String
    ): Set<String>? =
        run("match (category:Category) -[:PARENT_OF]-> (child) where ID(category)=$categoryId return category, child")
            .list { record: Record ->
                record["child"].asNode().id().toString()
            }
            .toSet()

}
