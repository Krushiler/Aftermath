plugins {
    id("java-library")
    id("kotlin-kapt")
    id("io.ktor.plugin")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("io.ktor:ktor-serialization-kotlinx-json")
}