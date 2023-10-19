val ktorVersion = "2.3.5"

plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
}

group = "com.krushiler"
version = "0.0.1"

application {
    mainClass.set("com.krushiler.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    fatJar {
        archiveFileName.set("server_fat.jar")
    }
}

dependencies {
    val exposedVersion = "0.41.1"
    val h2Version = "2.1.214"

    implementation(project(":api", "default"))

    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-websockets-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-network-tls-certificates")
    implementation("io.ktor:ktor-server-status-pages")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("com.h2database:h2:$h2Version")

    implementation("ch.qos.logback:logback-classic:1.4.11")

    implementation("io.insert-koin:koin-ktor:3.5.1")

    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.10")
}
