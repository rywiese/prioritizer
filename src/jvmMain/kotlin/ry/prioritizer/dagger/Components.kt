package ry.prioritizer.dagger

/**
 * Functions that wrap Dagger component builders.
 * This serves two purposes:
 * 1. to allow component creation via named parameters instead of builders.
 * 2. to encapsulate all Dagger auto-generated code in one file and thus prevent IDE errors
 *    from popping up in random files.
 *  If there are IDE errors in this file, just build the project to auto-generate the code.
 */
object Components {

    fun createPrioritizerComponent(
        host: String,
        port: Int,
        neo4jUri: String,
        neo4jUsername: String,
        neo4jPassword: String
    ): PrioritizerComponent =
        DaggerPrioritizerComponent.builder()
            .host(host)
            .port(port)
            .neo4jUri(neo4jUri)
            .neo4jUsername(neo4jUsername)
            .neo4jPassword(neo4jPassword)
            .build()

}
