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
val jwtVersion by extra("0.11.5")
val gsonVersion by extra("2.12.1")

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
	implementation("io.jsonwebtoken:jjwt-api:${jwtVersion}")
	implementation("io.jsonwebtoken:jjwt-impl:${jwtVersion}")
	implementation("io.jsonwebtoken:jjwt-jackson:${jwtVersion}")
	implementation("com.google.code.gson:gson:${gsonVersion}")
	testImplementation("org.projectlombok:lombok:1.18.26")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor ("org.mapstruct:mapstruct-processor:${mapstructVersion}")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<JavaCompile> {
	options.annotationProcessorPath = configurations.annotationProcessor.get()
}

tasks.withType<Test> {
	useJUnitPlatform()
}
