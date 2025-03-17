dependencies {
    // webflux - для запросов в сеть
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation(kotlin("test"))
    testImplementation("com.github.tomakehurst:wiremock-jre8:3.0.1")

    implementation(project(":utils"))
}