package com.yang.lib_common.down.a

/**
 * @Author Administrator
 * @ClassName DownListener
 * @Description
 * @Date 2021/11/30 16:26
 */
interface DownListener {

    fun startDown()

    fun stopDown()

    fun cancelDown()

    fun finishDown()

    fun successDown()

    fun errorDown()

}