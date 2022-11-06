package ry.prioritizer.ktor

import dagger.Module
import dagger.Provides
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import ry.prioritizer.ktor.plugins.HttpPlugin
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
        httpPlugin: HttpPlugin
    ): ApplicationEngine =
        embeddedServer(Netty, port = port, host = host) {
            install(ContentNegotiation) {
                json()
            }
            webGuiPlugin.applyPlugin(this)
            httpPlugin.applyPlugin(this)
        }

}
