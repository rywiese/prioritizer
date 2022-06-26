package ry.model

import ry.protocol.Identifiable

interface PriorityTree : Identifiable {

    val node: Node

    val childIds: Set<String>

}
