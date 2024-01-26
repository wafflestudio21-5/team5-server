import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
    kotlin("plugin.jpa") version "1.9.21"

    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
}

group = "com.wafflestudio.toyproject"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.awspring.cloud:spring-cloud-starter-aws:2.4.4")

    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

    runtimeOnly("com.h2database:h2")
    implementation("mysql:mysql-connector-java:8.0.28")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    environment("JWT_ACCESS_TOKEN_SECRET_KEY", "lWClWM3WU2fK/ieYRWrkDZ3rlEuXO4SlwBBC33oyDZo=")
    environment("JWT_REFRESH_TOKEN_SECRET_KEY", "WsLO6UkQ5XcDLp+gY1Usgamkhr5IBRI7ZBHgckYwCCA=")
    environment("OAUTH2_FACEBOOK_CLIENT_ID", "348359328040767")
    environment("OAUTH2_FACEBOOK_CLIENT_SECRET", "10f9eafe734151bcee983447aebf7d07")
}

tasks.getByName("jar") {
    enabled = false
}
