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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lookforpet.pet.data.PetData;
import com.lookforpet.pet.data.PetDataCloundDAO;

import java.io.File;
import java.io.FileNotFoundException;

public class CheckActivity extends AppCompatActivity {
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

    Uri FileUri;
    PetData p;

    public PetDataCloundDAO petDataCloundDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_check);

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
        //Log.d("txtpetName ",""+txtpetName);

        //接收照片
       // Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
        p = MainActivity.dao.getStudent(petName);
        ////本地端 抓照片位置
        String suri=p.uri;
        Log.d("suri--->",suri);

        //字串轉回 Uri
        FileUri =Uri.parse(suri);

        //本地端 抓照片位置
        ContentResolver cr = this.getContentResolver();
        try {
           Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(FileUri));//由抽象資料接口轉換圖檔路徑為Bitmap
            Log.d("bitmap", bitmap.toString());//寫log 若寫這個過去 只需要下面二行程式碼相同
            imagepic = (ImageView) findViewById(R.id.petImage);//取得圖片控制項ImageView
            imagepic.setImageBitmap(bitmap);// 將Bitmap設定到ImageView
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(), e);
        }

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
        Toast.makeText(CheckActivity.this, "資料已送出", Toast.LENGTH_LONG).show();
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
