plugins {
    kotlin("jvm") version "1.9.20"
    id("io.ktor.plugin") version "2.3.5"
    application
}

kotlin {
    jvmToolchain(21)
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("com.example.Application.Kt")
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    // Firebase BOM (update to the latest version if needed)
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    // Firebase Admin SDK (for backend)
    implementation("com.google.firebase:firebase-admin:9.2.0") // Use the latest version

    // Firebase Authentication (try without explicit version first)
    // implementation("com.google.firebase:firebase-auth-ktx")

    // Firebase Firestore
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Ktor dependencies
    implementation("io.ktor:ktor-server-core:2.3.5")
    implementation("io.ktor:ktor-server-netty:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.5")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.12")

    // Ktor Authentication and JWT
    implementation("io.ktor:ktor-server-auth:2.3.5")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.5")

    // Coroutines support for Firebase
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Testing dependencies
    testImplementation("io.ktor:ktor-server-tests:2.3.5")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.20")
}