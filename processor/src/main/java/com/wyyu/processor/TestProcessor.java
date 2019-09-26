package com.wyyu.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.wyyu.annotate.StringProcessor;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by wyyu on 2019-09-25.
 **/

// 这是一个注解处理器，用来生成 META-INF/services/javax.annotation.processing.Processor文件
// 引入方式 implementation 'com.google.auto.service:auto-service:1.0-rc4'
@AutoService(Processor.class) public class TestProcessor extends AbstractProcessor {

    //private Elements elementUtils;
    private Messager messager;
    //private Types typeUtils;
    private Filer filer;

    @Override public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        //elementUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        //typeUtils = processingEnvironment.getTypeUtils();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(
        //    StringProcessor.class)) {
        //    if (annotatedElement.getKind() != ElementKind.CLASS) {
        //        error(annotatedElement, "Only classes can be annotated with @%s",
        //            StringProcessor.class.getSimpleName());
        //        return true;
        //    }
        //    // 解析生成代码
        //    analysisAnnotated(annotatedElement);
        //}

        TypeSpec debugClass = TypeSpec.classBuilder(CLASS_NAME)
            .addModifiers(Modifier.PUBLIC)
            .build();

        JavaFile javaFile = JavaFile.builder(PACKAGE_NAME,debugClass).build();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        //把我们自己定义的注解添加进去
        annotations.add(StringProcessor.class.getCanonicalName());
        return annotations;
    }

    private void error(Element element, String msg, Object... objectArray) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, objectArray), element);
    }

    private static final String PACKAGE_NAME = "com.example.wyyu.gitsample.test.annotion";
    private static final String CLASS_NAME = "MethodProcessorTest";

    private void analysisAnnotated(Element annotatedElement) {
        StringProcessor processor = annotatedElement.getAnnotation(StringProcessor.class);
        String value = processor.value();

        StringBuilder builder = new StringBuilder().append("package " + PACKAGE_NAME + ";\n\n")
            .append("public class ")
            .append(CLASS_NAME)
            .append("{\n\n")
            .append("\tpublic String getValue(){\n")
            .append("\t\treturn ")
            .append(value)
            .append(";\n")
            .append("\t}\n")
            .append("}\n");

        try {
            JavaFileObject fileObject = filer.createSourceFile(PACKAGE_NAME + "." + CLASS_NAME);
            Writer writer = fileObject.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
