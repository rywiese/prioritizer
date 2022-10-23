package ry.prioritizer.neo4j

import org.neo4j.driver.Record
import org.neo4j.driver.Transaction

internal object Neo4JQueries {

    fun Transaction.getCategory(
        categoryId: String
    ): Neo4JCategory? =
        run("match (category:Category) where ID(category)=$categoryId return category")
            .list()
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

    fun Transaction.getQueue(
        categoryId: String
    ): List<Neo4JItem>? =
        run("match path=(category:Category)-[:NEXT_ITEM*0..]->(item) where ID(category)=$categoryId return item, LENGTH(path) as length")
            .list()
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

    fun Transaction.getRoot(): Neo4JTree? =
        run("match (root:Category) where not ()-[:PARENT_OF]->(root) return root")
            .list()
            .firstOrNull()
            ?.get("root")
            ?.let(CategoryParser::parse)
            ?.let { category: Neo4JCategory ->
                Neo4JTree(
                    category,
                    queue = getQueue(category.id)!!,
                    children = getChildIds(category.id)!!.map { childId: String ->
                        getTree(childId)!!
                    }
                )
            }

    fun Transaction.getTree(
        categoryId: String
    ): Neo4JTree? =
        getCategory(categoryId)?.let { category: Neo4JCategory ->
            Neo4JTree(
                category = category,
                queue = getQueue(category.id)!!,
                children = getChildIds(category.id)!!.map { childId: String ->
                    getTree(childId)!!
                }
            )
        }

    fun Transaction.getChildIds(
        categoryId: String
    ): Set<String>? =
        run("match (category:Category) -[:PARENT_OF]-> (child) where ID(category)=$categoryId return category, child")
            .list { record: Record ->
                record["child"].asNode().id().toString()
            }
            .toSet()

}
