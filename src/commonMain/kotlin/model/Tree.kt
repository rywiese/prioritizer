package model

import protocol.Identifiable

interface Tree : Identifiable {

    val name: String

    val queue: List<Item>

    val childIds: Set<String>

}
