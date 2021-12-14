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




//            val createSourceFile = processingEnv.filer.createSourceFile("com.yang.processor.VideoComponent")
//            val openWriter = createSourceFile.openWriter()
//            val elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(InjectViewModel::class.java)
//            openWriter.write("import com.yang.module_video.*;\n")
//            openWriter.write("@ActivityScope\n")
//            openWriter.write("public interface VideoComponent {\n")
//            elementsAnnotatedWith.forEach {
//                openWriter.write("void inject(${it.simpleName} m${it.simpleName});\n")
//                processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "============打印==${it.simpleName}")
//            }
//            openWriter.write("}")
//            openWriter.flush()
//            openWriter.close()
//
//            val classBuilder = TypeSpec.classBuilder("LogUtil").addModifiers(
//                Modifier.PUBLIC,
//                Modifier.FINAL
//            )
//
//            classBuilder.addMethod(
//                MethodSpec.methodBuilder("i")
//                    .addParameter(
//                        ParameterSpec.builder(TypeName.OBJECT, "msg", Modifier.FINAL).build()
//                    )
//                    .addCode("android.util.Log.i(\"TAG\", \"输出: \"+msg.toString());")
//                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
//                    .build()
//            )
//            classBuilder.addMethod(
//                MethodSpec.methodBuilder("d")
//                    .addParameter(
//                        ParameterSpec.builder(TypeName.OBJECT, "msg", Modifier.FINAL).build()
//                    )
//                    .addCode("android.util.Log.d(\"TAG\", \"输出: \"+msg.toString());")
//                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
//                    .build()
//            )
//            classBuilder.addMethod(
//                MethodSpec.methodBuilder("e")
//                    .addParameter(
//                        ParameterSpec.builder(TypeName.OBJECT, "msg", Modifier.FINAL).build()
//                    )
//                    .addCode("android.util.Log.e(\"TAG\", \"输出: \"+msg.toString());")
//                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
//                    .build()
//            )
//            val builder = JavaFile.builder("com.yang.processor", classBuilder.build()).build()
//            builder.writeTo(processingEnv.filer)
        } catch (e: Exception) {
            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "==============错误：${e.message}")
        }

        return false
    }
}