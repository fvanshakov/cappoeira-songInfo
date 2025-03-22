dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.opentable.components:otj-pg-embedded:1.0.1") // otj-pg-embedded

    implementation(project(":utils"))
}