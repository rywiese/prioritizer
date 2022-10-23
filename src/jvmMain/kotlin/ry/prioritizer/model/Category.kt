package ry.prioritizer.model

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore

@GraphQLIgnore
interface Category {

    val id: String

    val name: String

}
