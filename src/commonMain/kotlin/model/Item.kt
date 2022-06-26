package model

import protocol.Identifiable
import protocol.Named

data class Item(
    override val id: String,
    override val name: String,
    val price: Double,
    val link: String,
) : Identifiable, Named
