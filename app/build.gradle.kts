plugins {
    kotlin("jvm")
    application
}

group = "com.jarvis"
version = "0.1.0"

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("com.jarvis.core.application.JarvisApplicationKt")
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
