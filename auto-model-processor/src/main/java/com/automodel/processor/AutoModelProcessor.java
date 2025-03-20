package com.automodel.processor;

import com.automodel.annotation.AutoModel;
import com.automodel.annotation.AutoModelId;
import com.automodel.enums.ModelTypeEnum;
import com.automodel.util.CamelCaseConverter;
import com.squareup.javapoet.ClassName;
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
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rodger
 * @date 2025-03-04
 */
public class AutoModelProcessor extends AbstractProcessor {


    private ProcessingEnvironment processingEnv;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.processingEnv = processingEnv;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                "autoModel  processing");
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = env.getElementsAnnotatedWith(annotation);
            for (Element element : elements) {
                if (element.getKind() == ElementKind.CLASS) {
                    TypeElement classElement = (TypeElement) element;
                    AutoModel autoModel = classElement.getAnnotation(AutoModel.class);
                    for (ModelTypeEnum type : autoModel.value()) {
                        try {
                            this.generateDtoClass(classElement, type,autoModel.lombok()); // 生成对应类型 DTO
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
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
        return supportedAnnotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void generateDtoClass(TypeElement entityClass, ModelTypeEnum modelTypeEnum, boolean lombok) throws ClassNotFoundException {
        // 使用 JavaPoet 构建 DTO 类结构
        String className = entityClass.getSimpleName() + CamelCaseConverter.toUpperCamelCase(modelTypeEnum.name()) + "Dto";
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC);
        if (lombok) {
            classBuilder.addAnnotation(Class.forName("lombok.Data"));
        }
        // 遍历实体类字段
        for (Element field : entityClass.getEnclosedElements()) {
            if (field.getKind() == ElementKind.FIELD) {
                if ("serialVersionUID".equals(field.getSimpleName().toString())) {
                    continue;
                }
                if(ModelTypeEnum.ADD.equals(modelTypeEnum)){
                    if(field.getAnnotation(AutoModelId.class) != null){
                        continue;
                    }
                }
                if(ModelTypeEnum.DELETE.equals(modelTypeEnum)){
                    if(field.getAnnotation(AutoModelId.class) == null){
                        continue;
                    }
                }
                classBuilder.addField(
                        TypeName.get(field.asType()),
                        field.getSimpleName().toString(),
                        Modifier.PRIVATE
                );
            }
        }


        // 写入文件
        JavaFile javaFile = JavaFile.builder(
                processingEnv.getElementUtils().getPackageOf(entityClass).getQualifiedName().toString() + ".dto",
                classBuilder.build()
        ).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    "Failed to generate DTO class: " + e.getMessage());
        }
    }
}
