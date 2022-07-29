package model

import protocol.Identifiable

interface Tree : Identifiable {

    val name: String

    val queue: List<Item>

    val parentId: String?

    val childIds: Set<String>

}
