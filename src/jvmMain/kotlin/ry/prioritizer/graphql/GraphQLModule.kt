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
import com.expediagroup.graphql.server.types.GraphQLServerRequest
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import io.ktor.server.application.Application
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.receiveText
import ry.prioritizer.graphql.schema.mutations.CreateCategory
import ry.prioritizer.graphql.schema.mutations.CreateItem
import ry.prioritizer.graphql.schema.mutations.DeleteCategory
import ry.prioritizer.graphql.schema.mutations.PopItem
import ry.prioritizer.graphql.schema.queries.GetTree
import ry.prioritizer.graphql.schema.queries.Health
import ry.prioritizer.graphql.schema.queries.Root
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class GraphQLModule {

    companion object {

        @Provides
        @Singleton
        fun provideGraphQLContextFactory(): GraphQLContextFactory<GraphQLContext, ApplicationRequest> =
            object : GraphQLContextFactory<GraphQLContext, ApplicationRequest> {}

        @Provides
        @Singleton
        fun provideSchemaGeneratorConfig(): SchemaGeneratorConfig =
            SchemaGeneratorConfig(supportedPackages = listOf("ry.prioritizer.graphql.schema"))

        @Provides
        @Singleton
        fun provideGraphQLSchema(
            schemaGeneratorConfig: SchemaGeneratorConfig,
            health: Health,
            root: Root,
            getTree: GetTree,
            createCategory: CreateCategory,
            deleteCategory: DeleteCategory,
            createItem: CreateItem,
            popItem: PopItem,
        ): GraphQLSchema =
            toSchema(
                config = schemaGeneratorConfig,
                queries = listOf(
                    TopLevelObject(health),
                    TopLevelObject(root),
                    TopLevelObject(getTree),
                ),
                mutations = listOf(
                    TopLevelObject(createItem),
                    TopLevelObject(popItem),
                    TopLevelObject(createCategory),
                    TopLevelObject(deleteCategory)
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
        fun provideGraphQLRequestParser(
            objectMapper: ObjectMapper
        ): GraphQLRequestParser<ApplicationRequest> =
            object : GraphQLRequestParser<ApplicationRequest> {

                override suspend fun parseRequest(request: ApplicationRequest): GraphQLServerRequest? =
                    objectMapper.readValue(request.call.receiveText(), GraphQLServerRequest::class.java)

            }

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
