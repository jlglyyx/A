package com.yang.apt_processor

import com.google.auto.service.AutoService
import com.yang.apt_annotation.InjectViewModel
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
class Processor : AbstractProcessor() {

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "==============")
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val mutableSetOf = mutableSetOf<String>()
        mutableSetOf.add(InjectViewModel::class.java.canonicalName)
        return mutableSetOf
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return processingEnv.sourceVersion
    }

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        try {
//            val classBuilder = TypeSpec.classBuilder("AAAA").addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//            val get = ClassName.get("com.yang.module_video.ui.fragment", "VideoItemFragment")
//            classBuilder.addMethod(MethodSpec.constructorBuilder().build())
//            classBuilder.addMethod(MethodSpec.methodBuilder("test")
//                .addParameter(ParameterSpec.builder(get, "fragment", Modifier.FINAL).build())
//                .addCode("getVideoComponent(fragment).inject(fragment);")
//                .build())
//            val builder = JavaFile.builder("com.yang.processor", classBuilder.build()).build()
//            builder.writeTo(processingEnv.filer)
        } catch (e: Exception) {
            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "==============${e.message}")
        }

        return false
    }
}