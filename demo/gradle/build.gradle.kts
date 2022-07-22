plugins {
    java
}

java{
    sourceCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
}

repositories{
    mavenCentral()
}
dependencies{
    implementation(project(":platform:common-meta"))
    implementation(project(":platform:gradle"))
    implementation(gradleApi())
}

val jarArchiveName = "elsa-gradle-demo"

tasks.withType<Jar>{
    archiveBaseName.set(jarArchiveName)
}

task("updateLocalGradlePlugins"){
    dependsOn("build")
    group = "other"
    doLast{
        project.file("build/libs/${jarArchiveName}.jar").copyTo(File(projectDir, "dist/${jarArchiveName}.jar"), true)
    }
}

