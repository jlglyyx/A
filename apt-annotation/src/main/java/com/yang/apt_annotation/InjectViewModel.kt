package com.yang.apt_annotation

/**
 * @Author Administrator
 * @ClassName ViewModelInject
 * @Description
 * @Date 2021/12/8 17:28
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class InjectViewModel(val model:String)
