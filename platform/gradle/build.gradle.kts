plugins {
    java
}

repositories{
    mavenCentral()
}
dependencies{
    implementation(project(":platform:common-meta"))
    implementation(gradleApi())
}

java{
    sourceCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
}

val jarArchiveName = "elsa-gradle"

tasks.withType<Jar>{
    archiveBaseName.set(jarArchiveName)
    from(files(project.file("../../platform/common-meta/build/classes/java/main")))
}

task("updateLocalGradlePlugins"){
    dependsOn("build")
    group = "other"
    doLast{
        val gradleDir = File(projectDir.parentFile.parentFile, "gradle")
        project.file("build/libs/${jarArchiveName}.jar").copyTo(File(gradleDir, "${jarArchiveName}.jar"), true)
    }
}

