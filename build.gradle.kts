plugins {
    kotlin("multiplatform") version "1.6.21"
    kotlin("kapt") version "1.7.20"
    application
}

group = "ry"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("com.expediagroup:graphql-kotlin-spring-server:6.2.5")
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
                implementation("com.google.dagger:dagger:2.44")
                //kapt("com.google.dagger:dagger-compiler:2.44")
                configurations["kapt"].dependencies.add(
                    org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency(
                        "com.google.dagger",
                        "dagger-compiler",
                        "2.44"
                    )
                )
                implementation("io.ktor:ktor-server-netty:2.1.2")
                implementation("io.ktor:ktor-server-html-builder:2.1.2")
                implementation("org.neo4j.driver:neo4j-java-driver:4.4.9")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.290-kotlin-1.6.10")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.290-kotlin-1.6.10")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-css:17.0.2-pre.290-kotlin-1.6.10")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
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