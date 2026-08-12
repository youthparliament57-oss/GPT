pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(
        org.gradle.api.initialization.resolve.RepositoriesMode.FAIL_ON_PROJECT_REPOS
    )

    repositories {
        mavenCentral()
    }
}

rootProject.name = "JARVIS"

include(":app")
