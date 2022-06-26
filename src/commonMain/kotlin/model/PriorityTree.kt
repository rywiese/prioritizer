package model

import protocol.Identifiable

interface PriorityTree : Identifiable {

    val node: Node

    val childIds: Set<String>

}
