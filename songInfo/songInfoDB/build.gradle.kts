dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    implementation("org.hibernate.search:hibernate-search-mapper-orm:7.2.0.Final")
    implementation("org.hibernate.search:hibernate-search-backend-lucene:7.2.0.Final")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.opentable.components:otj-pg-embedded:1.0.1") // otj-pg-embedded

    implementation(project(":utils"))
}