plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    // implementation("org.slf4j:slf4j-simple:2.0.7")

    // suppress slf4j
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-nop
    implementation("org.slf4j:slf4j-nop:2.0.7")

    // https://mvnrepository.com/artifact/org.neo4j.driver/neo4j-java-driver
    implementation("org.neo4j.driver:neo4j-java-driver:5.7.0")

    // https://mvnrepository.com/artifact/org.neo4j/neo4j-graphql-java
    implementation("org.neo4j:neo4j-graphql-java:1.7.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}

fun TaskProvider<JavaCompile>.useUTF8() {
    get().options.compilerArgs.apply {
        add("-encoding")
        add("UTF-8")
    }
}

tasks.compileJava.useUTF8()
tasks.compileTestJava.useUTF8()
