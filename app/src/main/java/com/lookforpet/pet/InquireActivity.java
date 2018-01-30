package com.lookforpet.pet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class InquireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_inquire);
    }

}
