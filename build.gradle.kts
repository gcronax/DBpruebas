plugins {
    kotlin("jvm") version "2.2.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.xerial:sqlite-jdbc:3.43.0.0") //SQLite
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}