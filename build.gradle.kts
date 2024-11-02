plugins {
    id("java")
}

group = "com.hyfata.file.utils"
version = "1.1.0"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    implementation("org.apache.commons:commons-configuration2:2.9.0")
    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20240303")

}

tasks.test {
    useJUnitPlatform()
}