package http

data class CreateItemRequest(
    val name: String,
    val price: Double,
    val link: String
)
