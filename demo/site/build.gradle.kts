buildscript {
    dependencies{
        classpath(files(File(projectDir.parentFile.parentFile, "gradle/elsa-gradle.jar")
        ))

    }
}

apply<com.vga.platform.elsa.gradle.plugin.ElsaWebPlugin>()

configure<com.vga.platform.elsa.gradle.plugin.ElsaWebExtension>{
    codegen {
        remoting("src-gen", "demo-test-remoting.ts",
            arrayListOf("../server/code-gen/demo-elsa-remoting.xml") )
        l10n("src-gen", "demo-test-l10n.ts", "DemoL10nMessageBundle",
            arrayListOf("../server/code-gen/demo-elsa-site-l10n.xml")
        )
    }
}