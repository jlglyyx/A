package com.yang.apt_processor

import com.google.auto.service.AutoService
import com.squareup.javapoet.ClassName
import com.yang.apt_annotation.annotain.InjectViewModel
import com.yang.apt_annotation.data.ProxyInfo
import com.yang.apt_annotation.factory.ProcessorFactory
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
class Processor : AbstractProcessor() {

    private var proxyInfoMap = mutableMapOf<String, ProxyInfo>()

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
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
            proxyInfoMap.clear()
            val elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(InjectViewModel::class.java)
            /*获取注解信息*/
            elementsAnnotatedWith.forEach { element ->
                val enclosingElement = element.enclosingElement as TypeElement
                val qualifiedName = enclosingElement.qualifiedName.toString()
                var proxyInfo = proxyInfoMap[qualifiedName]
                if (null == proxyInfo){
                    proxyInfo = ProxyInfo()
                }
                val elementClassName = ClassName.get(element.asType())

                val annotation = element.getAnnotation(InjectViewModel::class.java)
                /*注解值*/
                proxyInfo.elementValue = annotation.value
                /*注解所在类名*/
                proxyInfo.className = enclosingElement.simpleName.toString()
                /*注解名集合*/
                proxyInfo.elementNameMap[element.toString()] = Class.forName(elementClassName.toString())

                proxyInfoMap[qualifiedName] = proxyInfo

                processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "==============错误：----${Class.forName(elementClassName.toString())}----\n ====================${elementClassName.javaClass}  ${elementClassName} \n${element} ${element.simpleName}    ${enclosingElement}  ${qualifiedName}  ${enclosingElement.getSimpleName()}")

            }

            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "==============zzzzzzzz${elementsAnnotatedWith}  ${elementsAnnotatedWith.size}：${proxyInfoMap.size}--------\n")
            /*工厂方法生成文件*/
            proxyInfoMap.forEach { (className, proxyInfo) ->
                    ProcessorFactory().getProcessor(proxyInfo.elementValue).createProcessor(className,proxyInfo,processingEnv)
            }

        } catch (e: Exception) {
            //processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "==============错误：${e.message}")
            return false
        }
        return true
    }
}