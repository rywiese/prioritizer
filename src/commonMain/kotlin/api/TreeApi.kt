package api

import model.Tree

interface TreeApi {

    suspend fun getRoot(
        treeDepth: Int,
        queueLength: Int
    ): Tree?

    /**
     * @param treeId
     * @param treeDepth
     * @param queueLength
     */
    suspend fun getTree(
        treeId: String,
        treeDepth: Int,
        queueLength: Int
    ): Tree?

}
