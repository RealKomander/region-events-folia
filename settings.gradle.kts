@file:Suppress("UnstableApiUsage")

rootProject.name = "region-events"

sequenceOf("api", "plugin").forEach {
  val kerbalProject = ":${rootProject.name}-$it"
  include(kerbalProject)
  project(kerbalProject).projectDir = file(it)
}
