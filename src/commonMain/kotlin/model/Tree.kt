package model

import protocol.Identifiable

interface Tree : Identifiable {

    val node: Node

    val queue: List<Item>

    val childIds: Set<String>

}
