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
        neo4JUri: String,
        neo4JUsername: String,
        neo4JPassword: String
    ): PrioritizerComponent =
        DaggerPrioritizerComponent.builder()
            .host(host)
            .port(port)
            .neo4JUri(neo4JUri)
            .neo4JUsername(neo4JUsername)
            .neo4JPassword(neo4JPassword)
            .build()

}
