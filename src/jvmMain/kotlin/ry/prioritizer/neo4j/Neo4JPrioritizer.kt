package ry.prioritizer.neo4j

import api.PrioritizerApi
import model.Category
import model.Item
import model.Tree
import org.neo4j.driver.Driver
import org.neo4j.driver.Transaction
import org.neo4j.driver.async.AsyncTransaction
import ry.prioritizer.neo4j.Neo4JQueries.createCategory
import ry.prioritizer.neo4j.Neo4JQueries.createItem
import ry.prioritizer.neo4j.Neo4JQueries.deleteCategory
import ry.prioritizer.neo4j.Neo4JQueries.getRoot
import ry.prioritizer.neo4j.Neo4JQueries.getTree
import ry.prioritizer.neo4j.Neo4JQueries.popItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Neo4JPrioritizer @Inject constructor(
    private val neo4jDriver: Driver
) : PrioritizerApi {

    override suspend fun getRoot(
        maxDepth: Int
    ): Tree? =
        neo4jDriver.asyncSession().readTransactionSuspend { transaction: AsyncTransaction ->
            transaction.getRoot(maxDepth)
        }

    override suspend fun getTree(
        categoryId: String,
        maxDepth: Int
    ): Tree? =
        neo4jDriver.asyncSession().readTransactionSuspend { transaction: AsyncTransaction ->
            transaction.getTree(categoryId, maxDepth)
        }

    override suspend fun createSubcategory(
        parentId: String,
        name: String
    ): Category? =
        neo4jDriver.asyncSession().writeTransactionSuspend { transaction: AsyncTransaction ->
            transaction.createCategory(parentId, name)
        }

    override suspend fun deleteCategory(
        categoryId: String
    ): String =
        neo4jDriver.asyncSession().writeTransactionSuspend { transaction: AsyncTransaction ->
            // TODO: figure out how to return `null` if not found.
            transaction.deleteCategory(categoryId).let { categoryId }
        }

    override suspend fun createItem(
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

    override suspend fun popItem(
        categoryId: String
    ): Item? =
        neo4jDriver.session().writeTransaction { transaction: Transaction ->
            transaction.popItem(categoryId)
        }

}
