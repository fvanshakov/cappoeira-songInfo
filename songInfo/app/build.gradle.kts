dependencies {
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("org.springframework.boot:spring-boot-starter")

    // метрики эндпоинтов
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation(project(":adminBoardClient"))
    implementation(project(":songInfoDB"))
    implementation(project(":utils"))

    testImplementation(kotlin("test"))
}