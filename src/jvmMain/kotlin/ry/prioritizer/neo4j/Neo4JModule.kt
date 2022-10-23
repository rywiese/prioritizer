package ry.prioritizer.neo4j

import dagger.Binds
import dagger.Module
import dagger.Provides
import org.neo4j.driver.AuthToken
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import ry.prioritizer.PrioritizerApi
import javax.inject.Named
import javax.inject.Singleton

@Module
interface Neo4JModule {

    @Binds
    @Singleton
    fun bindPrioritizerApi(neo4JPrioritizer: Neo4JPrioritizer): PrioritizerApi

    companion object {

        @Provides
        @Singleton
        fun provideAuthToken(
            @Named("neo4jUsername") username: String,
            @Named("neo4jPassword") password: String,
        ): AuthToken =
            AuthTokens.basic(username, password)

        @Provides
        @Singleton
        fun provideNeo4JDriver(
            @Named("neo4jUri") uri: String,
            authTokens: AuthToken
        ): Driver =
            GraphDatabase.driver(uri, authTokens)

    }

}
