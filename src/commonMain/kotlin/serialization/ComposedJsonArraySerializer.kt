package serialization

import kotlinx.serialization.json.JsonArray

class ComposedJsonArraySerializer<T>(
    private val composedSerializer: JsonSerializer<T>
) : JsonArraySerializer<List<T>> {

    override fun toJsonArray(t: List<T>): JsonArray =
        JsonArray(t.map(composedSerializer::toJson))

    override fun fromJsonArray(json: JsonArray): List<T> =
        json.map(composedSerializer::fromJson)

}
