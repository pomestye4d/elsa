plugins{
    java
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
    implementation("org.postgresql:postgresql:42.2.18")
    implementation("com.mchange:c3p0:0.9.5.5")
    implementation("org.springframework:spring-jdbc:5.3.21")
    implementation("org.springframework:spring-context:5.3.21")
    implementation(project(":platform:server-core"))
    implementation(project(":platform:common-core"))
}
