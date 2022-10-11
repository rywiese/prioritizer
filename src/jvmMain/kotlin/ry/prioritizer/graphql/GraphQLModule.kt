package ry.prioritizer.graphql

import com.expediagroup.graphql.dataloader.KotlinDataLoaderRegistryFactory
import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.execution.GraphQLContext
import com.expediagroup.graphql.generator.toSchema
import com.expediagroup.graphql.server.execution.GraphQLContextFactory
import com.expediagroup.graphql.server.execution.GraphQLRequestHandler
import com.expediagroup.graphql.server.execution.GraphQLRequestParser
import com.expediagroup.graphql.server.execution.GraphQLServer
import dagger.Binds
import dagger.Module
import dagger.Provides
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import io.ktor.server.application.Application
import io.ktor.server.request.ApplicationRequest
import ry.prioritizer.schema.mutations.AddChildToTree
import ry.prioritizer.schema.mutations.AddItemToQueue
import ry.prioritizer.schema.mutations.PopItemFromQueue
import ry.prioritizer.schema.queries.Health
import ry.prioritizer.schema.queries.Root
import ry.prioritizer.schema.queries.TreeByCategoryId
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class GraphQLModule {

    @Binds
    @Singleton
    abstract fun bindGraphQLRequestParser(
        ktorRequestParser: KtorRequestParser
    ): GraphQLRequestParser<ApplicationRequest>

    companion object {

        @Provides
        @Singleton
        fun provideGraphQLContextFactory(): GraphQLContextFactory<GraphQLContext, ApplicationRequest> =
            object : GraphQLContextFactory<GraphQLContext, ApplicationRequest> {}

        @Provides
        @Singleton
        fun provideSchemaGeneratorConfig(): SchemaGeneratorConfig =
            SchemaGeneratorConfig(supportedPackages = listOf("ry.prioritizer"))

        @Provides
        @Singleton
        fun provideGraphQLSchema(
            schemaGeneratorConfig: SchemaGeneratorConfig,
            root: Root,
            treeByCategoryId: TreeByCategoryId
        ): GraphQLSchema =
            toSchema(
                config = schemaGeneratorConfig,
                queries = listOf(
                    TopLevelObject(Health),
                    TopLevelObject(root),
                    TopLevelObject(treeByCategoryId),
                ),
                mutations = listOf(
                    TopLevelObject(AddItemToQueue),
                    TopLevelObject(PopItemFromQueue),
                    TopLevelObject(AddChildToTree)
                )
            )

        @Provides
        @Singleton
        fun provideGraphQL(
            graphQLSchema: GraphQLSchema
        ): GraphQL =
            GraphQL.newGraphQL(graphQLSchema)
                .build()

        @Provides
        @Singleton
        fun provideKotlinDataLoaderRegistryFactory(): KotlinDataLoaderRegistryFactory =
            KotlinDataLoaderRegistryFactory()

        @Provides
        @Singleton
        fun provideRequestHandler(
            graphQL: GraphQL,
            kotlinDataLoaderRegistryFactory: KotlinDataLoaderRegistryFactory
        ): GraphQLRequestHandler =
            GraphQLRequestHandler(
                graphQL,
                kotlinDataLoaderRegistryFactory
            )

        @Provides
        @Singleton
        fun provideGraphQLServier(
            requestParser: GraphQLRequestParser<ApplicationRequest>,
            contextFactory: GraphQLContextFactory<@JvmSuppressWildcards GraphQLContext, ApplicationRequest>,
            requestHandler: GraphQLRequestHandler
        ): GraphQLServer<ApplicationRequest> =
            GraphQLServer(
                requestParser,
                contextFactory,
                requestHandler
            )

        @Provides
        @Singleton
        @Named("playgroundHtml")
        fun providePlaygroundHtml(): String =
            Application::class.java.classLoader
                .getResource("graphql-playground.html")
                ?.readText()
                ?.replace("\${graphQLEndpoint}", "graphql")
                ?.replace("\${subscriptionsEndpoint}", "subscriptions")
                ?: throw IllegalStateException("graphql-playground.html cannot be found in the classpath")

    }

}
