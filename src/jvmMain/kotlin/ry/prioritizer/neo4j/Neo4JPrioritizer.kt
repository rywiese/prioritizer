package ry.prioritizer.neo4j

import org.neo4j.driver.Driver
import org.neo4j.driver.Transaction
import ry.prioritizer.PrioritizerApi
import ry.prioritizer.neo4j.Neo4JQueries.createCategory
import ry.prioritizer.neo4j.Neo4JQueries.createItem
import ry.prioritizer.neo4j.Neo4JQueries.deleteCategory
import ry.prioritizer.neo4j.Neo4JQueries.getRoot
import ry.prioritizer.neo4j.Neo4JQueries.getTree
import ry.prioritizer.neo4j.Neo4JQueries.popItem
import ry.prioritizer.schema.model.Category
import ry.prioritizer.schema.model.Item
import ry.prioritizer.schema.model.Tree
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Neo4JPrioritizer @Inject constructor(
    private val neo4jDriver: Driver
) : PrioritizerApi {

    override fun getRoot(): Tree? =
        neo4jDriver.session().readTransaction { transaction: Transaction ->
            transaction.getRoot()
        }

    override fun getTree(
        categoryId: String
    ): Tree? =
        neo4jDriver.session().readTransaction { transaction: Transaction ->
            transaction.getTree(categoryId)
        }

    override fun createCategory(
        parentId: String,
        name: String
    ): Category? =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            transaction.createCategory(parentId, name)
        }

    override fun deleteCategory(
        categoryId: String
    ): String =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            transaction.deleteCategory(categoryId).let { categoryId }
        }

    override fun createItem(
        categoryId: String,
        name: String,
        price: Double,
        link: String
    ): Item? =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            transaction.createItem(
                categoryId = categoryId,
                name = name,
                price = price,
                link = link
            )
        }

    override fun popItem(
        categoryId: String
    ): Item? =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            transaction.popItem(categoryId)
        }

}
