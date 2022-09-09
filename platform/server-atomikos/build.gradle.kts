plugins{
    java
    id("java-test-fixtures")
}
buildscript {
    dependencies{
        classpath(files(File(projectDir.parentFile.parentFile, "gradle/elsa-gradle.jar")))
    }
}

repositories{
    mavenCentral()
}
apply<com.vga.platform.elsa.gradle.plugin.ElsaJavaPlugin>()


dependencies{
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.springframework:spring-context:5.3.21")
    implementation("com.atomikos:transactions-jdbc:5.0.9")
    implementation("javax.transaction:jta:1.1")
    implementation("org.springframework:spring-tx:5.3.21")
    implementation("org.springframework:spring-jdbc:5.3.21")
    implementation(project(":platform:common-meta"))
    implementation(project(":platform:common-core"))
    implementation(project(":platform:server-core"))
    testFixturesImplementation(testFixtures(project(":platform:common-core")))
    testFixturesImplementation(testFixtures(project(":platform:server-core")))
    testFixturesImplementation("org.springframework:spring-context:5.3.21")
    testFixturesImplementation("org.springframework:spring-test:5.3.21")
    testFixturesImplementation("org.junit.jupiter:junit-jupiter:5+")
}


tasks.test {
    useJUnitPlatform()
}
