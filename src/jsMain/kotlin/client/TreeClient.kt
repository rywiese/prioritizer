package client

import api.MockTreeApi
import api.TreeApi
import model.DeepTree
import model.Depth1Tree
import model.Tree

object TreeClient {

    private val treeApi: TreeApi = MockTreeApi

    suspend fun getRootDepth1(): Depth1Tree? =
        treeApi
            .getRoot(
                treeDepth = 1,
                queueLength = -1
            )
            ?.let { tree: Tree ->
                Depth1Tree(tree as DeepTree)
            }

    suspend fun getTreeDepth1(
        treeId: String
    ): Depth1Tree? =
        treeApi
            .getTree(
                treeId = treeId,
                treeDepth = 1,
                queueLength = -1
            )
            ?.let { tree: Tree ->
                Depth1Tree(tree as DeepTree)
            }

}
