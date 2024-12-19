plugins {
    kotlin("jvm") version "1.9.20"
    id("io.ktor.plugin") version "2.3.5"
    application
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("com.example.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.firebase:firebase-admin:9.2.0") // Use the latest version
    implementation("io.ktor:ktor-server-core:2.3.5")
    implementation("io.ktor:ktor-server-netty:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.5")
    implementation("ch.qos.logback:logback-classic:1.4.12")
    implementation("io.ktor:ktor-server-auth:2.3.5")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.5")
    testImplementation("io.ktor:ktor-server-tests:2.3.5")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.20")
}