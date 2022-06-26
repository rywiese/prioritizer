package model

import protocol.Identifiable
import protocol.Named

data class Node(
    override val id: String,
    override val name: String
) : Identifiable, Named
