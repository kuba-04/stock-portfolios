group = "com.kuba"
version = "1.0-SNAPSHOT"

apply(plugin = "java-library")

repositories {
    mavenCentral()
}

dependencies {
    "testImplementation"("org.spockframework:spock-core:1.3-groovy-2.5")
}
