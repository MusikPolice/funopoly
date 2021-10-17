import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "ca.jonathanfritz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val hopliteVersion: String by project

dependencies {
    // marshals config from yaml files -  https://github.com/sksamuel/hoplite
    implementation("com.sksamuel.hoplite:hoplite-core:$hopliteVersion")
    implementation("com.sksamuel.hoplite:hoplite-yaml:$hopliteVersion")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "12"
}

application {
    mainClass.set("ca.jonathanfritz.funopoly.MainKt")
}