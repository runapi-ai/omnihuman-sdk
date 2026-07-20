plugins {
  `java-library`
  `maven-publish`
}

extra["runapiSlug"] = "omnihuman"

description = "RunAPI OmniHuman Java SDK for OmniHuman workflows."

java {
  withSourcesJar()
  withJavadocJar()
}

dependencies {
  api("ai.runapi:runapi-core:0.2.2")

  testImplementation(platform("org.junit:junit-bom:5.10.3"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      artifactId = "runapi-omnihuman"
      pom {
        name = "RunAPI OmniHuman Java SDK"
        description = "RunAPI OmniHuman Java SDK for OmniHuman workflows."
        url = "https://runapi.ai/models/omnihuman"
        licenses {
          license {
            name = "Apache License, Version 2.0"
            url = "https://www.apache.org/licenses/LICENSE-2.0"
          }
        }
        developers {
          developer {
            id = "runapi"
            name = "RunAPI"
            email = "contact@runapi.ai"
          }
        }
        scm {
          url = "https://github.com/runapi-ai/omnihuman-sdk"
          connection = "scm:git:https://github.com/runapi-ai/omnihuman-sdk.git"
          developerConnection = "scm:git:ssh://git@github.com/runapi-ai/omnihuman-sdk.git"
        }
      }
    }
  }
}
