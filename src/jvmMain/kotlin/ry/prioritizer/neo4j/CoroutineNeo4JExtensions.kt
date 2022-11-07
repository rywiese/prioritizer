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

suspend fun <T> AsyncSession.writeTransactionSuspend(
    work: suspend (AsyncTransaction) -> T
): T =
    coroutineScope {
        writeTransactionAsync { asyncTransaction: AsyncTransaction ->
            future {
                work(asyncTransaction)
            }
        }.await()
    }

suspend fun AsyncTransaction.runSuspend(
    query: String
): ResultCursor =
    runAsync(query).await()

suspend fun AsyncTransaction.runSuspendList(
    query: String
): List<Record> =
    runAsync(query)
        .thenCompose(ResultCursor::listAsync)
        .await()

suspend fun AsyncTransaction.runSuspendSingle(
    query: String
): Record? =
    runSuspendList(query).firstOrNull()
