package ry.prioritizer.dagger

import dagger.BindsInstance
import dagger.Component
import io.ktor.server.engine.ApplicationEngine
import ry.prioritizer.graphql.GraphQLModule
import ry.prioritizer.ktor.KtorModule
import ry.prioritizer.neo4j.Neo4JModule
import ry.prioritizer.serialization.SerializationModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        GraphQLModule::class,
        KtorModule::class,
        Neo4JModule::class,
        SerializationModule::class
    ]
)
interface PrioritizerComponent {

    val embeddedServer: ApplicationEngine

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun host(@Named("host") host: String): Builder

        @BindsInstance
        fun port(@Named("port") port: Int): Builder

        @BindsInstance
        fun neo4jUri(@Named("neo4jUri") neo4jUri: String): Builder

        @BindsInstance
        fun neo4jUsername(@Named("neo4jUsername") neo4jUsername: String): Builder

        @BindsInstance
        fun neo4jPassword(@Named("neo4jPassword") neo4jPassword: String): Builder

        fun build(): PrioritizerComponent

    }

}
