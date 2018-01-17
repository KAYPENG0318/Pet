package com.lookforpet.pet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void buttonAdd(View v)
    {
        Intent it=new Intent(MainActivity.this,AddActivity.class);
        startActivity(it);
    }
//    public void buttonInquire(View v)
//    {
//        Intent it=new Intent(MainActivity.this,InquireActivity.class);
//        startActivity(it);
//    }
}
