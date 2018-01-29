package com.lookforpet.pet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_main);



    }

    public void buttonAdd(View v)
    {
        Intent it=new Intent(MainActivity.this,AddActivity.class);
        startActivity(it);
    }
    public void buttonInquire(View v)
    {
        Intent it=new Intent(MainActivity.this,InquireActivity.class);
        startActivity(it);
    }
}
