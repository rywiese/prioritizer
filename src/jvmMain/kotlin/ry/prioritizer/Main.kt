package ry.prioritizer

import ry.prioritizer.dagger.Components

fun main() {
    Components
        .createPrioritizerComponent(
            host = "127.0.0.1",
            port = 8080,
            neo4jUri = System.getenv("NEO4J_URI"),
            neo4jUsername = System.getenv("NEO4J_USERNAME"),
            neo4jPassword = System.getenv("NEO4J_PASSWORD")
        )
        .embeddedServer
        .start(wait = true)
}
