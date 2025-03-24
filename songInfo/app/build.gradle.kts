dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation(project(":adminBoardClient"))

    implementation(project(":songInfoDB"))
    implementation(project(":utils"))
}