package ry.model

import ry.protocol.Identifiable
import ry.protocol.Named

data class Node(
    override val id: String,
    override val name: String
) : Identifiable, Named
