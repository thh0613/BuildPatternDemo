package com.taohuahua.apt_processor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * @Desc: 工具类
 * reated by taohuahua on 2019-01-28
 */
public class CustomElementUtils {
    public static String getPackageName(Elements elements, TypeElement typeElement) {
        return elements.getPackageOf(typeElement).getQualifiedName().toString();
    }

    public static String getSimpleName(TypeElement typeElement) {
        return typeElement.getSimpleName().toString();
    }

    public static String toUpperFirstChar(String charStr) {
        char[] charArray = charStr.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }
}
