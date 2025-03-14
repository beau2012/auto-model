package com.automodel.processor;

import com.automodel.annotation.AutoModel;
import com.automodel.enums.ModelTypeEnum;
import com.automodel.util.CamelCaseConverter;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rodger
 * @date 2025-03-04
 */
public class AutoModelProcessor extends AbstractProcessor {

    private Elements elements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elements = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        System.out.println("auto");
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = env.getElementsAnnotatedWith(annotation);
            for (Element element : elements) {
                if (element.getKind() == ElementKind.CLASS) {
                    TypeElement classElement = (TypeElement) element;
                    AutoModel autoModel = classElement.getAnnotation(AutoModel.class);
                    for (ModelTypeEnum type : autoModel.value()) {
                        generateDtoClass(classElement, type); // 生成对应类型 DTO
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedAnnotationTypes = new HashSet<>();
        supportedAnnotationTypes.add(AutoModel.class.getName());
        System.out.println(AutoModel.class.getName());
        return supportedAnnotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void generateDtoClass(TypeElement entityClass, ModelTypeEnum modelTypeEnum) {
        System.out.println("auto-model init ...");
        // 使用 JavaPoet 构建 DTO 类结构
        String className = entityClass.getSimpleName() + CamelCaseConverter.toUpperCamelCase(modelTypeEnum.name()) + "Dto";
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
//                .addAnnotation(Data.class)
                ;  // 假设使用 Lombok

        // 遍历实体类字段
        for (Element field : entityClass.getEnclosedElements()) {
            if (field.getKind() == ElementKind.FIELD) {
                if (!"serialVersionUID".equals(field.getSimpleName().toString())) {
                    classBuilder.addField(
                            TypeName.get(field.asType()),
                            field.getSimpleName().toString(),
                            Modifier.PRIVATE
                    );
                }
            }
        }


        // 写入文件
        JavaFile javaFile = JavaFile.builder(
                elements.getPackageOf(entityClass).getQualifiedName().toString() + ".dto",
                classBuilder.build()
        ).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
