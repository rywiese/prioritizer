package model

import protocol.Identifiable
import protocol.Named

data class Category(
    override val id: String,
    override val name: String,
) : Identifiable, Named
