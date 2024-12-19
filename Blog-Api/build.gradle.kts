dependencies {
    implementation("io.ktor:ktor-server-core:2.3.5")
    implementation("io.ktor:ktor-server-netty:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.5")
    implementation("ch.qos.logback:logback-classic:1.2.11") 
    implementation("io.ktor:ktor-server-auth:2.3.5")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.5")
    testImplementation("io.ktor:ktor-server-tests:2.3.5")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.20")
}