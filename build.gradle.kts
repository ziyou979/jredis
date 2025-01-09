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
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.24.1")
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.netty:netty-all:4.1.115.Final")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType(JavaCompile::class.java) {
    options.encoding = "UTF-8"
}