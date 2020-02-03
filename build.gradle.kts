group = "com.kuba"
version = "1.0-SNAPSHOT"

apply(plugin = "java-library")

plugins {
    groovy
    id("com.adarshr.test-logger") version "2.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    "testImplementation"("org.spockframework:spock-core:1.3-groovy-2.5")
    "compile"("org.projectlombok:lombok:1.18.10")
    "annotationProcessor"("org.projectlombok:lombok:1.18.4")
    "compile"("net.bytebuddy:byte-buddy-gradle-plugin:1.10.7")
}
