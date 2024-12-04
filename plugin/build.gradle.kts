plugins {
  alias(libs.plugins.blossom)
}

tasks {
  processResources {
    filesMatching("paper-plugin.yml") {
      expand("version" to project.version)
    }
  }
}

sourceSets {
  main {
    blossom {
      javaSources {
        property("version", project.version.toString())
      }
    }
  }
}

dependencies {
  api(project(":region-events-api"))

  compileOnly(libs.paper)
  compileOnly(libs.configurate)
  compileOnly(libs.worldguard)
}