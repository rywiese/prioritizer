package ry.prioritizer

import ry.prioritizer.dagger.Components

fun main() {
    Components
        .createPrioritizerComponent(
            host = "127.0.0.1",
            port = 8080,
            neo4JUri = System.getenv("NEO4J_URI"),
            neo4JUsername = System.getenv("NEO4J_USERNAME"),
            neo4JPassword = System.getenv("NEO4J_PASSWORD")
        )
        .embeddedServer
        .start(wait = true)
}
