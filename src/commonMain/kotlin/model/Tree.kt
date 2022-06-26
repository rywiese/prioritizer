package model

import protocol.Identifiable

interface Tree : Identifiable {

    val node: Node

    val childIds: Set<String>

    val queue: List<Item>

}
