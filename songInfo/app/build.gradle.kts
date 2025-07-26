dependencies {
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("io.prometheus:prometheus-metrics-exposition-formats:1.0.0")

    // метрики эндпоинтов
    implementation("io.micrometer:micrometer-core")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation(project(":adminBoardClient"))
    implementation(project(":songInfoDB"))
    implementation(project(":utils"))

    testImplementation(kotlin("test"))
}