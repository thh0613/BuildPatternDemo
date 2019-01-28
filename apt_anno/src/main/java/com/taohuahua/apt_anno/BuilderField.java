package com.taohuahua.apt_anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Desc:
 * created by taohuahua on 2019-01-28
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)

public @interface BuilderField {
    String value();
}
