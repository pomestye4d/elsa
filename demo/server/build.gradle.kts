plugins {
    id("org.springframework.boot") version "2.6.9"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    java
}
buildscript {
    dependencies {
        classpath(
            files(
                File(projectDir.parentFile.parentFile, "gradle/elsa-gradle.jar"),
                File(projectDir.parentFile, "gradle/dist/elsa-gradle-demo.jar")
            )
        )
    }
}
apply<com.vga.platform.elsa.gradle.plugin.ElsaJavaPlugin>()

apply<com.vga.platform.elsa.demo.gradle.ElsaDemoJavaPlugin>()

repositories {
    mavenCentral()
}
configure<com.vga.platform.elsa.gradle.plugin.ElsaJavaExtension> {
    codegen {
        domain(
            "src/main/java-gen",
            "com.vga.platform.elsa.demo.DemoElsaDomainMetaRegistryConfigurator",
            arrayListOf("code-gen/demo-elsa-domain.xml")
        )
        l10n("src/main/java-gen", "com.vga.platform.elsa.demo.DemoElsaL10nMetaRegistryConfigurator",  null,
            arrayListOf("code-gen/demo-elsa-site-l10n.xml"))
        remoting(
            "src/main/java-gen", "com.vga.platform.elsa.demo.DemoElsaRemotingMetaRegistryConfigurator",
            "com.vga.platform.elsa.demo.server.DemoElsaRestController",
            "com.vga.platform.elsa.demo.server.DemoElsaRemotingConstants", false,
            arrayListOf("code-gen/demo-elsa-remoting.xml")
        )
        uiTemplate(
            "src/main/java-gen", "elsa-demo-ui.xsd", "http://vga.com/elsa/demo-ui",
            arrayListOf("code-gen/demo-elsa-ui-template.xml")
        )
        ui("src/main/java-gen", arrayListOf("code-gen/demo-elsa-ui.xml"))
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.core:jackson-core:2+")
    implementation(testFixtures(project(":platform:server-core")))
    implementation(project(":platform:common-meta"))
    implementation(project(":platform:common-core"))
    implementation(project(":platform:server-core"))
    implementation(project(":platform:server-postgres"))
}

sourceSets.main {
    java.srcDirs("src/main/java", "src/main/java-gen")
}


