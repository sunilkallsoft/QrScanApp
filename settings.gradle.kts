pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://maven.google.com")
        maven(url ="https://jitpack.io" )
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
        maven(url = "https://jcenter.bintray.com/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.google.com")
        maven(url ="https://jitpack.io" )
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
        maven(url = "https://jcenter.bintray.com/")
    }
}

rootProject.name = "QRScanApp"
include(":app")
 