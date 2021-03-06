import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        flatDir {
            dirs("../aars")
        }
    }

    dependencies {
        classpath(Libs.kotlinGradle)
        classpath(Libs.kotlinterGradle)
        classpath(Libs.dependencyAnalyzer)
        classpath(Libs.kotlinAllOpen)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    `build-scan`
    id("io.gitlab.arturbosch.detekt") version Version.detektVersion
}

allprojects {
    apply(plugin = "kotlin-allopen")

    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
        flatDir {
            dirs("../aars")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.allWarningsAsErrors = true
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.apply {
            add("-Xlint:all")
            add("-Xlint:-deprecation") // AP-1900
            add("-Xlint:-unchecked") // dagger
            add("-Xlint:-rawtypes") // dagger
            add("-Xlint:-classfile") // dagger
            add("-Werror")
        }
    }
}
