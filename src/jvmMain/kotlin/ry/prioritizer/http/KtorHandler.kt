package ry.prioritizer.http

import io.ktor.server.application.ApplicationCall
import io.ktor.util.pipeline.PipelineContext

abstract class KtorHandler : suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit {

    protected abstract suspend fun PipelineContext<Unit, ApplicationCall>.handle()

    override suspend fun invoke(
        pipelineContext: PipelineContext<Unit, ApplicationCall>,
        igmore: Unit
    ) {
        pipelineContext.handle()
    }

}
