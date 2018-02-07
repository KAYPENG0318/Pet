package com.lookforpet.pet;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lookforpet.pet.data.PetData;
import com.lookforpet.pet.data.PetDataCloundDAO;

import java.io.FileNotFoundException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    //LAYOUT 資料填入
    ImageView imagepic;

    String petName ;
    String petKind ;
    String petAge ;
    String petSex;
    String petType;
    String petCity,petArea,petAddress;
    String ownerName,ownerTel,ownerLine,ownerEmail;
    String Date;
    TextView txtpetName, txtpetKind, txtpetAge,txtpetSex,txtpetType,txtpetCity,txtpetArea,txtpetAddress;
    TextView txtownerName,txtownerTel,txtownerLine,txtownerEmail;
    TextView txtdate;

    //PetData p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_detail);
        //照片
        imagepic =(ImageView)findViewById(R.id.petImage);

        //寵物資料
        txtpetName = (TextView) findViewById(R.id.petName);
        txtpetKind = (TextView) findViewById(R.id.petKind);
        txtpetAge = (TextView) findViewById(R.id.petAge);
        txtpetSex = (TextView) findViewById(R.id.petSex);
        txtpetType = (TextView) findViewById(R.id.petType);
        //失蹤地點
        txtpetCity = (TextView) findViewById(R.id.petCity);
        txtpetArea = (TextView) findViewById(R.id.petArea);
        txtpetAddress = (TextView) findViewById(R.id.petAddress);
        //主人
        txtownerName = (TextView) findViewById(R.id.ownerName);
        txtownerTel = (TextView) findViewById(R.id.ownerTel);
        txtownerLine = (TextView) findViewById(R.id.ownerLine);
        txtownerEmail = (TextView)findViewById(R.id.ownerEmail);
        //日期
        txtdate=(TextView)findViewById(R.id.systemData);

        //取出資料

        String petName=getIntent().getStringExtra("petName");
        String petKind=getIntent().getStringExtra("petKind");
        String petAge=getIntent().getStringExtra("petAge");
        String petSex=getIntent().getStringExtra("petSex");
        String petType=getIntent().getStringExtra("petType");
        String petCity=getIntent().getStringExtra("petCity");
        String petArea=getIntent().getStringExtra("petArea");
        String petAddress=getIntent().getStringExtra("petAddress");
        String ownerName=getIntent().getStringExtra("ownerName");
        String ownerTel=getIntent().getStringExtra("ownerTel");
        String ownerLine=getIntent().getStringExtra("ownerLine");
        String ownerEmail=getIntent().getStringExtra("ownerEmail");
        String date=getIntent().getStringExtra("date");
        String uri=getIntent().getStringExtra("uri");


       // String petName=getIntent().getStringExtra("petName");


        //接收照片
        // Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
       // PetData p  = MainActivity.dao.getStudent(petName);

       // Glide.with(DetailActivity.this).load(p.uri).into(imagepic);



        txtpetName.setText(petName);
        txtpetKind.setText(petKind);
        Log.d("test",petKind);
        txtpetAge.setText(petAge);
        txtpetSex.setText(petSex);
        txtpetType.setText(petType);
        txtpetCity.setText(petCity);
        txtpetArea.setText(petArea);
        txtpetAddress.setText(petAddress);
        //主人資料
        txtownerName.setText(ownerName);
        txtownerTel.setText(ownerTel);
        txtownerLine.setText(ownerLine);
        txtownerEmail.setText(ownerEmail);
        txtdate.setText(date);

    }

    public void buttonMain(View v)
    {
        Intent it = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(it);
        finish();
    }
}
