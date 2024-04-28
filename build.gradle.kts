plugins {
    java
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("Main")
}

tasks.register<JavaExec>("runInteractive") {
    group = "application"
    mainClass.set("Main")
    classpath = sourceSets["main"].runtimeClasspath
    standardInput = System.`in`
}