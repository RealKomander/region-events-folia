plugins {
  `java-library`
  alias(libs.plugins.spotless)
  alias(libs.plugins.indra)
  alias(libs.plugins.shadow)
}


subprojects {
  apply(plugin = "java-library")
  apply(plugin = "com.diffplug.spotless")
  apply(plugin = "net.kyori.indra")
  apply(plugin = "com.gradleup.shadow")

  repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.enginehub.org/repo/")
  }

  indra {
    javaVersions {
      target(21)
      minimumToolchain(21)
    }
  }

  spotless {
    java {
      licenseHeaderFile("$rootDir/header/header.txt")
      trimTrailingWhitespace()
      indentWithSpaces(2)
    }
    kotlinGradle {
      trimTrailingWhitespace()
      indentWithSpaces(2)
    }
  }

  tasks {
    compileJava {
      dependsOn("spotlessApply")
      options.compilerArgs.add("-parameters")
    }

    shadowJar {
      archiveBaseName.set(project.name)
    }
  }
}
