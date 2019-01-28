package com.taohuahua.apt_processor;

import com.taohuahua.apt_anno.BuilderField;

import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/**
 * @Desc: 工具
 * created by taohuahua on 2019-01-28
 */
public class ValidCheckUtil {
    public static boolean isValidClass(Element element) throws Exception {
        if (element.getKind() != ElementKind.CLASS) {
            return false;
        }

        Set<Modifier> classModifiers = element.getModifiers();
        if (!classModifiers.contains(Modifier.PUBLIC)) {
            throw new Exception("the class is not public");
        }

        if (classModifiers.contains(Modifier.ABSTRACT)) {
            throw new Exception("the class is not allowed abstract");
        }

        return true;
    }

    public static boolean isValidField(Element element) throws Exception {
        if (element.getKind() != ElementKind.FIELD || !(element instanceof VariableElement)) {
            return false;
        }

        BuilderField fieldAnno = element.getAnnotation(BuilderField.class);
        if (fieldAnno == null) {
            return false;
        }

        Set<Modifier> fieldModifiers = element.getModifiers();

        if (fieldModifiers.contains(Modifier.FINAL)) {
            throw new Exception("the field is not allowed final");
        }

        if (fieldModifiers.contains(Modifier.STATIC)) {
            throw new Exception("the field is not allowed static");
        }

        if (fieldModifiers.contains(Modifier.PRIVATE)) {
            throw new Exception("the field is not allowed private");
        }

        return true;
    }
}
