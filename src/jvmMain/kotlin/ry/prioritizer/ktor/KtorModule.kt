package ry.prioritizer.ktor

import dagger.Module
import dagger.Provides
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import ry.prioritizer.ktor.plugins.GraphQLPlugin
import ry.prioritizer.ktor.plugins.PrioritizerHttpPlugin
import ry.prioritizer.ktor.plugins.WebGuiPlugin
import javax.inject.Named
import javax.inject.Singleton

@Module
object KtorModule {

    @Provides
    @Singleton
    fun provideEmbeddedServer(
        @Named("host") host: String,
        @Named("port") port: Int,
        webGuiPlugin: WebGuiPlugin,
        httpPlugin: PrioritizerHttpPlugin
    ): ApplicationEngine =
        embeddedServer(Netty, port = port, host = host) {
            webGuiPlugin.applyPlugin(this)
            httpPlugin.applyPlugin(this)
        }

}
