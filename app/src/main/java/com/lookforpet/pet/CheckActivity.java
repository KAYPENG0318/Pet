package com.lookforpet.pet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lookforpet.pet.data.PetData;
import com.lookforpet.pet.data.PetDataCloundDAO;

public class CheckActivity extends AppCompatActivity {
    ImageView imageView;

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

    PetData p;

    public PetDataCloundDAO petDataCloundDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_check);

        //照片
        imageView =(ImageView)findViewById(R.id.petImage);


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




        petDataCloundDAO= new PetDataCloundDAO(CheckActivity.this);

        showDetail();

    }

    //呈現輸入 樣子
    public void  showDetail()
    {

        String petName=getIntent().getStringExtra("petName");
        Log.d("txtpetName ",""+txtpetName);

        //接收照片
       // Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("BitmapImage");

       // imageView.setImageBitmap(bitmap);// 將Bitmap設定到ImageView

        p = MainActivity.dao.getStudent(petName);
        Log.d("test~",p.petName);
        txtpetName.setText(p.petName);
        txtpetKind.setText(p.petKind);
        Log.d("test",p.petKind);
        txtpetAge.setText(p.petAge);
        txtpetSex.setText(p.petSex);
        txtpetType.setText(p.petType);
        txtpetCity.setText(p.petCity);
        txtpetArea.setText(p.petArea);
        txtpetAddress.setText(p.petAddress);
        //主人資料
        txtownerName.setText(p.ownerName);
        txtownerTel.setText(p.ownerTel);
        txtownerLine.setText(p.ownerLine);
        txtownerEmail.setText(p.ownerEmail);
        txtdate.setText(p.date);


    }



    //送到FIREBASE
    public void buttonSend(View v)
    {

        //把資料送到firebase另一個類別處理
        // 加到 Clound 上  把etDataCloundDAO
        petDataCloundDAO.add(p);

        Intent it =new Intent(CheckActivity.this,MainActivity.class);
        startActivity(it);
        finish();

    }

    //回前一頁
    public void buttonBack(View v)
    {
        finish();
    }
}
