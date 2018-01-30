package com.lookforpet.pet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lookforpet.pet.data.PetDAO;

public class MainActivity extends AppCompatActivity {
    //靜態變數
    final public static PetDAO dao= new PetDAO();

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
        //新增 20180130
        finish();
    }
    public void buttonInquire(View v)
    {
        Intent it=new Intent(MainActivity.this,InquireActivity.class);
        startActivity(it);
        //新增 20180130
        finish();
    }
}
