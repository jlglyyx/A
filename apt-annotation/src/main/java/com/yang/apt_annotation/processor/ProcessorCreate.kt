package com.yang.apt_annotation.processor

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import com.yang.apt_annotation.data.ProxyInfoData
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Modifier

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

    /**
     * 存放 创建的ViewModel属性名 属性的类型 防止存在多个同类型的不同名称的viewmodel
     * key == pictureViewModelClass value == com.yang.module_picture.viewmodel.PictureViewModel
     */
    private val mutableMap = mutableMapOf<String, String>()

    override fun createProcessor(
        className: String,
        proxyInfoData: ProxyInfoData,
        processingEnv: ProcessingEnvironment
    ) {
        //parseProcessor()
        /**
         * 创建的文件名
         * createSourceFile == PictureFragment_InjectViewModel
         */
        val createSourceFile = processingEnv.filer.createSourceFile("com.yang.processor.${proxyInfoData.className}_InjectViewModel")

        val openWriter = createSourceFile.openWriter()

        /**
         * 枚举
         */
        val elementNameListBuilder = StringBuilder()

        /**
         * 包名StringBuilder
         */
        val packageStringBuilder: StringBuilder = StringBuilder()

        /**
         * t == pictureViewModel u == com.yang.module_picture.viewmodel.PictureViewModel
         */
        proxyInfoData.elementNameMap.forEach { (t, u) ->

            /**
             * 注解的变量名
             *  @InjectViewModel
             *  var pictureViewModule: PictureViewModel
             * 生成 pictureViewModelClass
             */
            var forName = "${t}Class"

            /**
             * PictureViewModel
             */
            val substring = u.substring(u.lastIndexOf(".") + 1)

            if(mutableMap.containsValue(u)){
                /**
                 * 如果存在相同类型的ViewModel 则取之前缓存的key
                 * at == pictureViewModelClass au == com.yang.module_picture.viewmodel.PictureViewModel
                 * 例如： Class<PictureViewModel> pictureViewModuleClass = PictureViewModel.class;
                 * viewModelStoreOwner.pictureViewModule = DaggerUtil.getViewModel(viewModelStoreOwner, factory, pictureViewModuleClass);
                 * viewModelStoreOwner.pictureViewModuleaa = DaggerUtil.getViewModel(viewModelStoreOwner, factory, pictureViewModuleClass);
                 */
                mutableMap.forEach { (at, au) ->
                    if (au == u){
                        forName = at
                    }
                }
            }else{
                /**
                 * 如果不存在则加入缓存
                 */
                elementNameListBuilder.append("Class<$substring> $forName = $substring.class;\n")
                packageStringBuilder.append("import $u;\n")
                mutableMap[forName] = u
            }

            /**
             * 生成viewModel
             */
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
        openWriter.write("package com.yang.processor;\n\n")
        openWriter.write(packageStringBuilder.toString()+"\n")
        openWriter.write("public class ${proxyInfoData.className}_InjectViewModel implements InjectManager<$className>{\n")
        openWriter.write(
            "   @Override\n" +
                    "    public void inject($className viewModelStoreOwner) {\n" +
                    "        ${prefixName}Component ${fieldName}Component = ${prefixName}DaggerHelp.get${prefixName}Component(viewModelStoreOwner);\n" +
                    "        ${fieldName}Component.inject(viewModelStoreOwner);\n" +
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


    private fun parseProcessor(){

        val build = TypeSpec.classBuilder("HHHH_InjectViewModel").addModifiers(Modifier.PUBLIC, Modifier.FINAL).build()
        val builder = JavaFile.builder("com.yang.processor", build).build()
        builder.writeTo(System.out)

//        TypeSpec.classBuilder("com.yang.processor.${proxyInfoData.className}_InjectViewModel")
    }

}