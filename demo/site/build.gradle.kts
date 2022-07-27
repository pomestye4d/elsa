buildscript {
    dependencies{
        classpath(files(File(projectDir.parentFile.parentFile, "gradle/elsa-gradle.jar"),
            File(projectDir.parentFile, "gradle/dist/elsa-gradle-demo.jar")
        ))

    }
}

apply<com.vga.platform.elsa.gradle.plugin.ElsaWebPlugin>()

apply<com.vga.platform.elsa.demo.gradle.ElsaDemoJavaPlugin>()

configure<com.vga.platform.elsa.gradle.plugin.ElsaWebExtension>{
    codegen {
        declareImport("src/features/list-template.tsx", "ListTemplate")
        declareImport("src/features/table-template.ts", "TableTemplate")
        remoting("src/gen", "demo-test-remoting.ts",
            arrayListOf("../server/code-gen/demo-elsa-remoting.xml") )
        l10n("src/gen", "demo-test-l10n.ts", "DemoL10nMessageBundle",
            arrayListOf("../server/code-gen/demo-elsa-site-l10n.xml")
        )
        uiTemplate("src/gen", "demo-ui-template.ts",
            arrayListOf("../server/code-gen/demo-elsa-ui-template.xml"))
        ui("src/gen", "demo-ui.ts",
            arrayListOf("../server/code-gen/demo-elsa-ui.xml"))
    }
}
