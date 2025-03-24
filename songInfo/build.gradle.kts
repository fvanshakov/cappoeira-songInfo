plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
}

allprojects {
	group = "ru.cappoeira.songInfo"
	version = "1.0.0"

	repositories {
		mavenCentral()
	}
}

springBoot {
	mainClass.set("ru.cappoeira.songInfo.SongInfoApplicationKt")
}

subprojects {
	apply(plugin = "kotlin")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.springframework.boot")

	springBoot {
		mainClass.set("ru.cappoeira.songInfo.SongInfoApplicationKt")
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.jetbrains.kotlin:kotlin-reflect")

		testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		testImplementation("org.junit.jupiter:junit-jupiter")
		testImplementation("io.mockk:mockk:1.13.17")
	}

	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(17)
		}
	}

	kotlin {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict")
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
