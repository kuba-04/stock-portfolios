group = "com.kuba"
version = "1.0-SNAPSHOT"

apply(plugin = "java-library")

plugins {
    groovy
}

repositories {
    mavenCentral()
}

dependencies {
    "testImplementation"("org.spockframework:spock-core:1.3-groovy-2.5")
    "compile"("org.projectlombok:lombok:1.18.10")
    "annotationProcessor"("org.projectlombok:lombok:1.18.4")
}
