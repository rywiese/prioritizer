package ry.prioritizer.ktor

import dagger.Module
import dagger.Provides
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import ry.prioritizer.ktor.plugins.RestPlugin
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
        restPlugin: RestPlugin
    ): ApplicationEngine =
        embeddedServer(Netty, port = port, host = host) {
            install(CallLogging)
            install(ContentNegotiation) {
                json()
            }
            webGuiPlugin.applyPlugin(this)
            restPlugin.applyPlugin(this)
        }

}
