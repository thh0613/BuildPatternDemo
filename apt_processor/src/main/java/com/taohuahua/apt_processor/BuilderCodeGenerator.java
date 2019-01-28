package com.taohuahua.apt_processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.taohuahua.apt_anno.BuilderField;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * @Desc: created by taohuahua on 2019-01-28
 */
public class BuilderCodeGenerator {
    private Elements mElementUtils;
    private Filer mFiler;
    private List<MethodSpec> mMethodSpecs = new ArrayList<>();
    private List<FieldSpec> mFieldSpecs = new ArrayList<>();
    private MethodSpec mBuildMethodSpec;

    public BuilderCodeGenerator(Elements elementUtils, Filer filer) {
        mElementUtils = elementUtils;
        mFiler = filer;
    }

    public void generatorCode(TypeElement typeElement, List<VariableElement> fields) {
        String packageName = CustomElementUtils.getPackageName(mElementUtils, typeElement);
        JavaFile javaFile = JavaFile.builder(packageName, generateTypeCode(typeElement, fields))
                .build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private TypeSpec generateTypeCode(TypeElement typeElement, List<VariableElement> fields) {
        generateFieldMethodCode(typeElement, fields);
        generateMethodCode(typeElement, fields);
        generateBuildMethodCode(typeElement, fields);

        return TypeSpec.classBuilder(CustomElementUtils.getSimpleName(typeElement) + "Builder")
                .addModifiers(Modifier.PUBLIC)
                .addMethods(mMethodSpecs)
                .addFields(mFieldSpecs)
                .addMethod(mBuildMethodSpec)
                .build();
    }

    private void generateFieldMethodCode(TypeElement typeElement, List<VariableElement> fields) {
        for (VariableElement field : fields) {
            BuilderField fieldAnno = field.getAnnotation(BuilderField.class);
            TypeName typeName = TypeName.get(field.asType());
            FieldSpec.Builder fieldSpec = FieldSpec.builder(typeName,fieldAnno.value());
            mFieldSpecs.add(fieldSpec.build());
        }
    }

    private void generateMethodCode(TypeElement typeElement, List<VariableElement> fields) {
        for (VariableElement field : fields) {
            ClassName className = ClassName.get(CustomElementUtils.getPackageName(mElementUtils, typeElement), "AptStudentBuilder");
            BuilderField fieldAnno = field.getAnnotation(BuilderField.class);
            String methodName = "set" + CustomElementUtils.toUpperFirstChar(fieldAnno.value());
            TypeName typeName = TypeName.get(field.asType());
            String text = "this.{0} = {1};";
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(className)
                    .addCode(MessageFormat.format(text, fieldAnno.value(), fieldAnno.value()))
                    .addCode("\n")
                    .addStatement("return this")
                    .addParameter(typeName, fieldAnno.value());
            mMethodSpecs.add(methodBuilder.build());
        }
    }


    /**
     * $T: 类型替换 addStatement("$T file", File.class)  ＝> File file
     * $L: 字面量替换 addStatement("$L = null", "file") => file = null
     * $S: 字符串 addStatement("file = new File($S)", "foo/bar") => file = new File("foo/bar")
     * $N: 名称替换 MethodSpec methodSpec = MethodSpec.methodBuilder("foo").build();
     * ("$N", methodSpec) => foo.
     * @param typeElement
     * @param fields
     */
    private MethodSpec generateBuildMethodCode(TypeElement typeElement, List<VariableElement> fields) {
        String methodName = "build";
        TypeName typeName = TypeName.get(typeElement.asType());

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(typeName);

        String code = "{0} obj = new {1}();\n";
        methodBuilder.addCode(MessageFormat.format(code, typeName, typeName));

        for (VariableElement field : fields) {
            BuilderField fieldAnno = field.getAnnotation(BuilderField.class);
            String codeTxt = "obj.{0} = {1};\n";
            methodBuilder.addCode(MessageFormat.format(codeTxt, fieldAnno.value(), fieldAnno.value()));
        }

        methodBuilder.addStatement("return obj");
        mBuildMethodSpec = methodBuilder.build();
        return mBuildMethodSpec;
    }

}
