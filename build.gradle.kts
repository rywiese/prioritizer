import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

val coroutinesVersion = "1.7.3"
val daggerVersion = "2.47"
val kotlinWrappersVersion = "1.0.0-pre.602"
val ktorVersion = "2.3.2"
val neo4jVersion = "4.4.9"
val openApiVersion = "6.2.1"

fun kotlinw(target: String): String = "org.jetbrains.kotlin-wrappers:kotlin-$target"

plugins {
    val kotlinVersion = "1.9.0"
    val openApiGeneratorVersion = "6.2.1"
    kotlin("multiplatform") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    id("org.openapi.generator") version openApiGeneratorVersion
    application
}

group = "ry"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig(Action {
                cssSupport {
                    enabled.set(true)
                }
            })
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("com.google.dagger:dagger:$daggerVersion")
                configurations["kapt"].dependencies.add(
                    org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency(
                        "com.google.dagger",
                        "dagger-compiler",
                        daggerVersion
                    )
                )
                implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-server-html-builder:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("org.neo4j.driver:neo4j-java-driver:$neo4jVersion")
                implementation("org.openapitools:openapi-generator-gradle-plugin:$openApiVersion")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation(enforcedPlatform(kotlinw("wrappers-bom:$kotlinWrappersVersion")))
                implementation(kotlinw("emotion"))
                implementation(kotlinw("react"))
                implementation(kotlinw("react-dom"))
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-js:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("ry.prioritizer.MainKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}

val openApiInputSpec = "src/jvmMain/resources/PrioritizerApi.yaml"

openApiValidate {
    inputSpec.set(openApiInputSpec)
    recommend.set(true)
}

openApiGenerate {
    inputSpec.set(openApiInputSpec)
    generatorName.set("kotlin")
}

rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin::class.java) {
    // Failing on the yarn lock is a no-go as long as `./gradlew kotlinUpgradeYarnLock` fails with 139
    rootProject.the<YarnRootExtension>().yarnLockMismatchReport = YarnLockMismatchReport.WARNING // NONE | FAIL
    rootProject.the<YarnRootExtension>().reportNewYarnLock = false
    rootProject.the<YarnRootExtension>().yarnLockAutoReplace = true
}
