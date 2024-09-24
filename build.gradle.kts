plugins {
    id("java")
}

group = "org.zy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
    implementation("org.apache.logging.log4j:log4j-core:2.24.0")
    implementation("org.slf4j:slf4j-log4j12:2.0.16")
    implementation("org.slf4j:slf4j-api:2.0.16")
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.netty:netty-all:4.1.113.Final")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType(JavaCompile::class.java) {
    options.encoding = "UTF-8"
}