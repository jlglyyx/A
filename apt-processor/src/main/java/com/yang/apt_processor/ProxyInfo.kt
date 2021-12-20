package com.yang.apt_processor

import javax.lang.model.element.Element

/**
 * @Author Administrator
 * @ClassName ProxyInfo
 * @Description
 * @Date 2021/12/20 15:36
 */
class ProxyInfo {

    var mInjectElements = mutableMapOf<String, Element>()

    var className:String? = null

}