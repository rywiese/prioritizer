package ry.prioritizer.neo4j

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.future.future
import org.neo4j.driver.Record
import org.neo4j.driver.async.AsyncSession
import org.neo4j.driver.async.AsyncTransaction
import org.neo4j.driver.async.ResultCursor


suspend fun <T> AsyncSession.readTransactionSuspend(
    work: suspend (AsyncTransaction) -> T
): T =
    coroutineScope {
        readTransactionAsync { asyncTransaction: AsyncTransaction ->
            future {
                work(asyncTransaction)
            }
        }.await()
    }

suspend fun AsyncTransaction.runList(
    query: String
): List<Record> =
    runAsync(query)
        .thenCompose(ResultCursor::listAsync)
        .await()
