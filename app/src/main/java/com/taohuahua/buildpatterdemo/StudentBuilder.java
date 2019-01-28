package com.taohuahua.buildpatterdemo;

/**
 * @Desc: created by taohuahua on 2019-01-28
 */
public class StudentBuilder {
    private String mName;
    private int mAge;
    private int mGender;
    private int mEnglishScore;
    private int mMathScore;

    public StudentBuilder setName(String name) {
        mName = name;
        return this;
    }

    public StudentBuilder setAge(int age) {
        mAge = age;
        return this;
    }

    public StudentBuilder setGender(int gender) {
        mGender = gender;
        return this;
    }

    public StudentBuilder setEnglishScore(int englishScore) {
        mEnglishScore = englishScore;
        return this;
    }

    public StudentBuilder setMathScore(int mathScore) {
        mMathScore = mathScore;
        return this;
    }

    public Student build() {
        Student student = new Student();
        student.mName = mName;
        student.mAge = mAge;
        student.mGender = mGender;
        student.mEnglishScore = mEnglishScore;
        student.mMathScore = mMathScore;
        return student;
    }



}
