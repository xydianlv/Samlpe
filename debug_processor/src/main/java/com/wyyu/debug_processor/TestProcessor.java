package com.wyyu.debug_processor;

import com.google.auto.service.AutoService;
import com.wyyu.debug_annotate.StringAnnotate;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class) public class TestProcessor extends AbstractProcessor {

    private static final String PACKAGE_NAME = "com.example.wyyu.gitsamlpe.test.annotion";
    private static final String CLASS_NAME = "DebugAnnotateImpl";

    //private Messager messager;
    //private Filer filer;

    @Override public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        //messager = processingEnvironment.getMessager();
        //filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        processorDebug(roundEnvironment);

        return true;
    }

    @Override public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        // 把自己定义的注解添加进去
        annotations.add(StringAnnotate.class.getCanonicalName());
        return annotations;
    }

    private void processorDebug(RoundEnvironment roundEnv) {

        Map<String, String> valueMap = new HashMap<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(StringAnnotate.class)) {
            if (element instanceof TypeElement) {
                String keyClass = ((TypeElement) element).getQualifiedName().toString();
                String value = element.getAnnotation(StringAnnotate.class).value();

                valueMap.put(keyClass, value);
            }
        }

        generateDebugImpl(valueMap);
    }

    private void generateDebugImpl(Map<String, String> valueMap) {
        StringBuilder builder = new StringBuilder();

        builder.append("package ").append(PACKAGE_NAME).append(";\n\n");

        builder.append("import com.wyyu.debug_annotate.IDebugAnnotate;\n");
        builder.append("import java.util.HashMap;\n");
        builder.append("import java.util.Map;\n\n");

        builder.append("public final class ").append(CLASS_NAME);
        builder.append(" implements IDebugAnnotate {\n");

        builder.append("    private Map<Class<?>, String> valueMap;\n");

        builder.append("    public ").append(CLASS_NAME).append("() {\n");
        builder.append("        valueMap = new HashMap<>();\n\n");

        if (valueMap != null && !valueMap.isEmpty()) {
            for (String key : valueMap.keySet()) {
                builder.append("        valueMap.put(").append(key).append(".class, \"");
                builder.append(valueMap.get(key)).append("\");\n");
            }
        }

        builder.append("    }\n\n");

        builder.append("    @Override\n");
        builder.append("    public String getValue(Class<?> keyClass) {\n");
        builder.append("        return valueMap.get(keyClass);\n");
        builder.append("    }\n\n");

        builder.append("}\n");

        try {
            JavaFileObject object =
                processingEnv.getFiler().createSourceFile(PACKAGE_NAME + "." + CLASS_NAME);
            Writer writer = object.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
