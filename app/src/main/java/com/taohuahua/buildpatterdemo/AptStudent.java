package com.taohuahua.buildpatterdemo;

import com.taohuahua.apt_anno.Builder;
import com.taohuahua.apt_anno.BuilderField;

/**
 * @Desc: created by taohuahua on 2019-01-28
 */

@Builder("AptStudent")
public class AptStudent {

    @BuilderField("name")
    public String name;

    @BuilderField("age")
    public int age;

    @BuilderField("gender")
    public int gender;

    @BuilderField("englishScore")
    public int englishScore;

    @BuilderField("mathScore")
    public int mathScore;
}
