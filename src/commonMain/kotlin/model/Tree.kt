package model

import protocol.Identifiable
import protocol.Named

interface Tree : Identifiable, Named {

    val queue: List<Item>

    val parentId: String?

    val childIds: Set<String>

    fun subTree(childId: String): Tree?

    fun limitDepth(depth: Int): Tree

}
