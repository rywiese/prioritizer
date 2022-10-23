package ry.prioritizer.model

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore

@GraphQLIgnore
interface Item {

    val id: String

    val name: String

    val price: Double

    val link: String

}
