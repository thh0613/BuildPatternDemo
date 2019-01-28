package com.taohuahua.buildpatterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private AptStudent mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AptStudentBuilder builder = new AptStudentBuilder();
        mStudent = builder.setName("thh").setGender(0).build();
        Log.i("thh ", mStudent.name + " " + mStudent.age);
    }
}
