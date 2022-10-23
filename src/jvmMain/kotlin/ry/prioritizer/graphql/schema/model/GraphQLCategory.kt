package ry.prioritizer.graphql.schema.model

import com.expediagroup.graphql.generator.annotations.GraphQLName
import ry.prioritizer.model.Category

@GraphQLName("Category")
data class GraphQLCategory(
    override val id: String,
    override val name: String
) : Category {

    companion object {

        fun fromCategory(
            category: Category
        ): GraphQLCategory =
            category as? GraphQLCategory ?: GraphQLCategory(
                id = category.id,
                name = category.name
            )

    }

}
