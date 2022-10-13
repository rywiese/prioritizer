package ry.prioritizer.schema.model

import java.util.UUID

data class Item(
    val id: String,
    val name: String,
    val price: Double,
    val link: String,
)
