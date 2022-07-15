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

configure<com.vga.platform.elsa.gradle.plugin.ElsaJavaExtension>{
  codegen {
    l10n("src/main/java-gen",
    "com.vga.platform.elsa.server.core.CoreL10nMessagesRegistryConfigurator",
        "com.vga.platform.elsa.server.core.CoreL10nMessagesRegistryFactory", arrayListOf("src/main/codegen/core-server-l10n-messages.xml")
    )
  }
}

dependencies{
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.springframework:spring-web:5.3.21")
    implementation("org.springframework:spring-context:5.3.21")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("io.projectreactor:reactor-core:3.4.19")
    implementation("com.fasterxml.jackson.core:jackson-core:2.13.3")
    implementation("com.nothome:javaxdelta:2.0.1")
    implementation("org.ehcache:ehcache:3.9.9")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.springframework:spring-tx:5.3.21")
    implementation("org.springframework:spring-jdbc:5.3.21")
    implementation(project(":platform:common-meta"))
    implementation(project(":platform:common-core"))
    testFixturesImplementation(testFixtures(project(":platform:common-core")))
    testFixturesImplementation("org.springframework:spring-jdbc:5.3.21")
    testFixturesImplementation("org.springframework:spring-context:5.3.21")
    testFixturesImplementation("org.springframework:spring-test:5.3.21")
    testFixturesImplementation("org.junit.jupiter:junit-jupiter:5+")
    testFixturesImplementation("com.mchange:c3p0:0.9+")
    testFixturesImplementation("org.hsqldb:hsqldb:2+")
}


sourceSets.main{
    java.srcDirs("src/main/java","src/main/java-gen")
}

tasks.test {
    useJUnitPlatform()
}
