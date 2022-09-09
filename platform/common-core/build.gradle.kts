plugins{
    id("java-test-fixtures")
    java
}
buildscript {
    dependencies{
        classpath(files(File(projectDir.parentFile.parentFile, "gradle/elsa-gradle.jar")))
    }
}
apply<com.vga.platform.elsa.gradle.plugin.ElsaJavaPlugin>()

configure<com.vga.platform.elsa.gradle.plugin.ElsaJavaExtension>{
    codegen {
        domain("src/testFixtures/java-gen",
            "com.vga.platform.elsa.common.core.test.ElsaCommonCoreTestDomainMetaRegistryConfigurator", arrayListOf("src/testFixtures/codegen/elsa-core-test-domain.xml"))
        domain("src/main/java-gen",
            "com.vga.platform.elsa.common.domain.core.ElsaCommonCoreDomainMetaRegistryConfigurator", arrayListOf("src/main/codegen/elsa-core-domain.xml"))
        custom(
            "src/main/java-gen",
            "com.vga.platform.elsa.config.ElsaCommonCoreCustomMetaRegistryConfigurator", arrayListOf("src/main/codegen/elsa-core-custom.xml"))
        remoting(
            "src/main/java-gen",
            "com.vga.platform.elsa.config.ElsaCommonCoreRemotingMetaRegistryConfigurator", arrayListOf("src/main/codegen/elsa-core-remoting.xml"))
    }
}

repositories{
    mavenCentral()
}
dependencies{
//    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.13.3")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("org.springframework:spring-context:5.3.21")
    implementation(project(":platform:common-meta"))
    testFixturesImplementation(project(":platform:common-meta"))
    testFixturesImplementation("org.springframework:spring-test:5.3.21")
    testFixturesImplementation("ch.qos.logback:logback-classic:1.2.11")
    testFixturesImplementation("org.junit.platform:junit-platform-suite:1+")
    testFixturesImplementation("org.junit.jupiter:junit-jupiter:5+")
    testFixturesImplementation("org.springframework:spring-context:5.3.21")

}


sourceSets.main{
    java.srcDirs("src/main/java","src/main/java-gen")
}

sourceSets.testFixtures{
    java.srcDirs("src/testFixtures/java-gen")
}

tasks.test {
    useJUnitPlatform()
}
