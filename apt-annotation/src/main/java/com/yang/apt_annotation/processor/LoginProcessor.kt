package com.yang.apt_annotation.processor

import com.yang.apt_annotation.data.ProxyInfo
import javax.annotation.processing.ProcessingEnvironment
import javax.tools.Diagnostic

/**
 * @Author Administrator
 * @ClassName LoginProcessor
 * @Description
 * @Date 2021/12/21 14:32
 */
class LoginProcessor : IProcessor {

    private var prefixName = "Login"
    private var fieldName = "mLogin"
    private var packageName = "login"

    override fun createProcessor(
        className: String,
        proxyInfo: ProxyInfo,
        processingEnv: ProcessingEnvironment
    ) {
        val createSourceFile =
            processingEnv.filer.createSourceFile("com.yang.processor.${proxyInfo.className}_InjectViewModel")
        val openWriter = createSourceFile.openWriter()

        val elementNameListBuilder = StringBuilder()
        proxyInfo.elementNameMap.forEach { (t, u) ->
            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "==============正确：${u}")
            elementNameListBuilder.append( "viewModelStoreOwner.$t = DaggerUtil.getViewModel(viewModelStoreOwner, factory, $u);\n" +
                    "            ")
        }

        openWriter.write("package com.yang.processor;\n")
        openWriter.write(
            "import com.yang.apt_annotation.manager.InjectManager;\n" +
                    "import com.yang.lib_common.util.DaggerUtil;\n" +
                    "import com.yang.module_${packageName}.di.component.Dagger${prefixName}Component;\n" +
                    "import com.yang.module_${packageName}.di.component.${prefixName}Component;\n" +
                    "import com.yang.module_${packageName}.di.factory.${prefixName}ViewModelFactory;\n" +
                    "import com.yang.module_${packageName}.helper.${prefixName}DaggerHelp;\n" +
                    "import org.jetbrains.annotations.NotNull;\n" +
                    "import java.lang.reflect.Field;\n" +
                    "import javax.inject.Provider;\n"
        )
        openWriter.write("import $className;\n")
        openWriter.write("public class ${proxyInfo.className}_InjectViewModel implements InjectManager<$className>{\n")
        openWriter.write(
            "   @Override\n" +
                    "    public void inject($className viewModelStoreOwner) {\n" +
                    "      ${prefixName}Component ${fieldName}Component = ${prefixName}DaggerHelp.get${prefixName}Component(viewModelStoreOwner);\n" +
                    "      ${fieldName}Component.inject(viewModelStoreOwner);\n" +
                    "        try {\n" +
                    "            Class<Dagger${prefixName}Component> dagger${prefixName}ComponentClass = Dagger${prefixName}Component.class;\n" +
                    "            Field provide${prefixName}ViewModelFactoryProvider = dagger${prefixName}ComponentClass.getDeclaredField(\"provide${prefixName}ViewModelFactoryProvider\");\n" +
                    "            provide${prefixName}ViewModelFactoryProvider.setAccessible(true);\n" +
                    "            Provider<${prefixName}ViewModelFactory> o = (Provider<${prefixName}ViewModelFactory>) provide${prefixName}ViewModelFactoryProvider.get(${fieldName}Component);\n" +
                    "            ${prefixName}ViewModelFactory factory = o.get();\n" +
                    "            $elementNameListBuilder\n" +
                    "        } catch (Exception e) {\n" +
                    "            e.printStackTrace();\n" +
                    "        }\n" +
                    "    }\n"
        )
        openWriter.write("}")
        openWriter.flush()
        openWriter.close()
    }

}