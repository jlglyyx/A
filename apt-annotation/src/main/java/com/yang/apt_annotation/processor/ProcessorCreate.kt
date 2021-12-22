package com.yang.apt_annotation.processor

import com.yang.apt_annotation.data.ProxyInfoData
import javax.annotation.processing.ProcessingEnvironment

/**
 * @Author Administrator
 * @ClassName LoginProcessor
 * @Description
 * @Date 2021/12/21 14:32
 */
class ProcessorCreate(
    private var prefixName: String,
    private var fieldName: String,
    private var packageName: String
) : IProcessor {

    private val mutableMap = mutableMapOf<String, String>()

    override fun createProcessor(
        className: String,
        proxyInfoData: ProxyInfoData,
        processingEnv: ProcessingEnvironment
    ) {
        val createSourceFile =
            processingEnv.filer.createSourceFile("com.yang.processor.${proxyInfoData.className}_InjectViewModel")
        val openWriter = createSourceFile.openWriter()

        val elementNameListBuilder = StringBuilder()

        val packageStringBuilder: StringBuilder = StringBuilder()

        proxyInfoData.elementNameMap.forEach { (t, u) ->

            var forName = "${t}Class"

            val substring = u.substring(u.lastIndexOf(".") + 1)

            if(mutableMap.containsValue(u)){
                mutableMap.forEach { (at, au) ->
                    if (au == u){
                        forName = at
                    }
                }
            }else{
                elementNameListBuilder.append("Class<$substring> $forName = (Class<$substring>) Class.forName(\"$u\");\n")
                packageStringBuilder.append("import $u;")
                mutableMap[forName] = u
            }

            elementNameListBuilder.append("            viewModelStoreOwner.$t = DaggerUtil.getViewModel(viewModelStoreOwner, factory, $forName);\n")
        }
        packageStringBuilder.append(
            "import com.yang.apt_annotation.manager.InjectManager;\n" +
                    "import com.yang.lib_common.util.DaggerUtil;\n" +
                    "import com.yang.module_${packageName}.di.component.Dagger${prefixName}Component;\n" +
                    "import com.yang.module_${packageName}.di.component.${prefixName}Component;\n" +
                    "import com.yang.module_${packageName}.di.factory.${prefixName}ViewModelFactory;\n" +
                    "import com.yang.module_${packageName}.helper.${prefixName}DaggerHelp;\n" +
                    "import org.jetbrains.annotations.NotNull;\n" +
                    "import java.lang.reflect.Field;\n" +
                    "import javax.inject.Provider;\n" +
                    "import $className;\n"
        )
        openWriter.write("package com.yang.processor;\n")
        openWriter.write(packageStringBuilder.toString())
        openWriter.write("public class ${proxyInfoData.className}_InjectViewModel implements InjectManager<$className>{\n")
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