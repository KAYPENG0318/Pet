package com.lookforpet.pet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_check);
    }

    public void buttonSend(View v)
    {
        Intent it=new Intent(CheckActivity.this,MainActivity.class);
        startActivity(it);
    }
    public void buttonBack(View v)
    {
        finish();
    }
}
