package client

import api.MockTreeApi
import api.TreeApi
import model.DeepTree
import model.Depth1Tree
import model.Tree

/**
 * Facade that delegates to [TreeApi] with all depths set to 1. Performs unsafe casts into [Depth1Tree]s for the client.
 */
object TreeClient {

    private val treeApi: TreeApi = MockTreeApi

    suspend fun getRoot(
        sideEffect: (Depth1Tree) -> Unit
    ): Depth1Tree? =
        getRoot()?.also(sideEffect)

    suspend fun getRoot(): Depth1Tree? =
        treeApi
            .getRoot(
                treeDepth = 1,
                queueLength = -1
            )
            ?.assumeDepth1()

    suspend fun getTree(
        treeId: String,
        sideEffect: (Depth1Tree) -> Unit
    ): Depth1Tree? =
        getTree(treeId)?.also(sideEffect)

    suspend fun getTree(
        treeId: String
    ): Depth1Tree? =
        treeApi
            .getTree(
                treeId = treeId,
                treeDepth = 1,
                queueLength = -1
            )
            ?.assumeDepth1()

    suspend fun promote(
        treeId: String,
        childId: String,
        sideEffect: (Depth1Tree) -> Unit
    ): Depth1Tree? =
        promote(treeId = treeId, childId = childId)
            ?.also(sideEffect)

    suspend fun promote(
        treeId: String,
        childId: String
    ): Depth1Tree? =
        treeApi
            .promote(
                treeId = treeId,
                childId = childId,
                treeDepth = 1,
                queueLength = -1
            )
            ?.assumeDepth1()

    private fun Tree.assumeDepth1(): Depth1Tree = Depth1Tree(this as DeepTree)

}
