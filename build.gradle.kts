plugins {
	java
	id("org.springframework.boot") version "2.5.14"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.core"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(11))
	}
}

val springdocVersion by extra("1.6.7")
val mapstructVersion by extra("1.6.3")

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.session:spring-session-core")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.hibernate:hibernate-core")
	implementation("org.springdoc:springdoc-openapi-ui:${springdocVersion}")
	implementation("org.mapstruct:mapstruct:${mapstructVersion}")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
