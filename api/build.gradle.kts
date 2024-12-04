plugins {
  `maven-publish`
}

dependencies {
  compileOnly(libs.paper)
  compileOnly(libs.worldguard)
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      from(components["java"])
    }
  }
}