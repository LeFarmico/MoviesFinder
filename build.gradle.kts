buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(Plugins.gradle)
        classpath(Plugins.kotlinGradle)
        classpath(Plugins.kotlinAndroidExt)
        classpath(Plugins.ktlint)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
    }
}
