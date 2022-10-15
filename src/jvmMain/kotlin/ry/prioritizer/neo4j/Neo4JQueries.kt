package ry.prioritizer.neo4j

import org.neo4j.driver.Record
import org.neo4j.driver.Transaction
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
            ?.let(CategoryParser::parse)

    fun Transaction.createCategory(
        parentId: String,
        name: String
    ): Category? =
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
    ): Item? =
        run("match (category:Category)-[:NEXT_ITEM*0..]->(item) where ID(category)=$categoryId and NOT (item)-[:NEXT_ITEM]->() create (item)-[:NEXT_ITEM]->(new:Item{name:\"$name\",price:$price,link:\"$link\"}) return new")
            .list()
            .firstOrNull()
            ?.get("new")
            ?.let(ItemParser::parse)

    fun Transaction.popItem(
        categoryId: String
    ): Item? =
        run("match path=(category:Category)-[:NEXT_ITEM*1..2]->(item:Item) where ID(category)=$categoryId return item, LENGTH(path) as length")
            .list { record: Record ->
                val numberOfItems: Int = record["length"].asInt()
                val item: Item = ItemParser.parse(record["item"])
                numberOfItems to item
            }
            .sortedBy { (numberOfItems: Int, _) ->
                numberOfItems
            }
            .map { (_, item: Item) ->
                item
            }
            .let { items: List<Item> ->
                items.getOrNull(0) to items.getOrNull(1)
            }
            .let { (itemToPop: Item?, nextItem: Item?) ->
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
    ): List<Item>? =
        run("match path=(category:Category)-[:NEXT_ITEM*0..]->(item) where ID(category)=$categoryId return item, LENGTH(path) as length")
            .list()
            .takeIf(List<*>::isNotEmpty)
            ?.drop(1)
            ?.map { record: Record ->
                val numberOfItems: Int = record["length"].asInt()
                val item: Item = ItemParser.parse(record["item"])
                numberOfItems to item
            }
            ?.sortedBy { (numberOfItems: Int, _) ->
                numberOfItems
            }
            ?.map { (_, item: Item) ->
                item
            }

    fun Transaction.getRoot(): Tree? =
        run("match (root:Category) where not ()-[:PARENT_OF]->(root) return root")
            .list()
            .firstOrNull()
            ?.get("root")
            ?.let(CategoryParser::parse)
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

    fun Transaction.getChildIds(
        categoryId: String
    ): Set<String>? =
        run("match (category:Category) -[:PARENT_OF]-> (child) where ID(category)=$categoryId return category, child")
            .list { record: Record ->
                record["child"].asNode().id().toString()
            }
            .toSet()

}
