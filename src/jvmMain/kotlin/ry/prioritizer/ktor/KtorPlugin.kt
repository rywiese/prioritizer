package ry.prioritizer.ktor

import io.ktor.server.application.Application

abstract class KtorPlugin {

    fun applyPlugin(application: Application) = application.configure()

    protected abstract fun Application.configure()

}
