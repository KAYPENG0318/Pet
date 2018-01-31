package com.lookforpet.pet;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lookforpet.pet.data.PetData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    ImageView imageView;
    EditText edpetName,edpetAge;
    EditText edownerName,edownerTel,edownerLine,edownerEmail;
    Spinner spkind,spsex,sptype;
    String petSex;
    String petType;
    String petName ;
    String petKind ;
    String petAge ;
    String petCity,petArea,petAddress;
    String ownerName,ownerTel,ownerLine,ownerEmail;
    String Date;

    private File tempFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_add);
        this.tempFile = new File("/sdcard/a.jpg");
        //找尋Button按鈕
        Button bt1 = (Button)findViewById(R.id.petButton);
        bt1.setText("選擇圖片");//設定按鈕內文字
        //設定按鈕監聽式
        bt1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //開啟Pictures畫面Type設定為image
                intent.setType("image/*");
                //使用Intent.ACTION_GET_CONTENT這個Action  //會開啟選取圖檔視窗讓您選取手機內圖檔
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("crop", "true");// crop=true 有這句才能叫出裁剪頁面.
                intent.putExtra("aspectX", 1);// 这兩項為裁剪框的比例.
                intent.putExtra("aspectY", 1);// x:y=1:1
                intent.putExtra("output", Uri.fromFile(tempFile));
                intent.putExtra("outputFormat", "JPEG");//返回格式
                //取得相片後返回本畫面
                startActivityForResult(Intent.createChooser(intent,"選擇圖片"),1);
            }

        });

        //寵物名
        edpetName=(EditText)findViewById(R.id.petName);

        //下拉式選單：種類
         spkind = (Spinner)findViewById(R.id.petKind);
        ArrayAdapter<CharSequence> choosekind = ArrayAdapter.createFromResource(AddActivity.this,
                R.array.kind,android.R.layout.simple_spinner_dropdown_item);
        spkind.setAdapter(choosekind);
        spkind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                petKind=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //年紀
        edpetAge=(EditText)findViewById(R.id.petAge);

        //下拉式選單：性別
        spsex = (Spinner)findViewById(R.id.petSex);
        ArrayAdapter<CharSequence> choosesex = ArrayAdapter.createFromResource(AddActivity.this,
                R.array.sex,android.R.layout.simple_spinner_dropdown_item);
        spsex.setAdapter(choosesex);
        spsex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                petSex=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //下拉式選單：體型
        sptype = (Spinner)findViewById(R.id.petType);
        ArrayAdapter<CharSequence> choosetype = ArrayAdapter.createFromResource(AddActivity.this,
                R.array.type,android.R.layout.simple_spinner_dropdown_item);
        sptype.setAdapter(choosetype);
        sptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                petType=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //失蹤地點
        petCity="台北市";

        petArea="大同區";

        petAddress="承德路一段1號";

        //飼主資料

        edownerName=(EditText)findViewById(R.id.ownerName);

        edownerTel=(EditText)findViewById(R.id.ownerTel);

        edownerLine=(EditText)findViewById(R.id.ownerLine);

        edownerEmail=(EditText)findViewById(R.id.ownerEmail);;



        //顯示填單日期
        Calendar mCal = Calendar.getInstance();
        CharSequence s = DateFormat.format("yyyy-MM-dd", mCal.getTime());    // kk:24小時制, hh:12小時制
        Date=s.toString();
        TextView tv=(TextView)findViewById(R.id.systemData);
        tv.setText(String.valueOf(s.toString()));

    }


    //取得相片後返回的監聽式
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         imageView = (ImageView) findViewById(R.id.petImage);
        //當使用者按下確定後
        if (resultCode == RESULT_OK) {

            // 設定到ImageView
            imageView.setImageDrawable(Drawable.createFromPath(tempFile.getAbsolutePath()));

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    //填好資料送到確定頁
    public void clickOk(View v)
    {

        petName = edpetName.getText().toString();
        Log.d("-<-petName-->",petName);
        petAge=edpetAge.getText().toString();
        ownerName=edownerName.getText().toString();
        ownerTel=edownerTel.getText().toString();
        ownerLine=edownerLine.getText().toString();
        ownerEmail=edownerEmail.getText().toString();


//        //再按一次就在arraylist 新增一筆 PetData
        if( MainActivity.dao.getList().size()>0)
        {
            //先arraylist 裡拿掉 物件
            MainActivity.dao.getList().remove(0);
            //在 arraylist  增加一筆物件
            MainActivity.dao.add(new PetData(petName,petKind,petAge,petSex,petType,petCity,petArea,petAddress,ownerName,ownerTel,ownerLine,ownerEmail,Date));
        }else{
            //有資料在 listarray裡了
            MainActivity.dao.add(new PetData(petName,petKind,petAge,petSex,petType,petCity,petArea,petAddress,ownerName,ownerTel,ownerLine,ownerEmail,Date));
        }

        Log.d("","AddActivity----"+MainActivity.dao.getList().size());

        //資料送到  CheckActivity
        Intent it = new Intent(AddActivity.this, CheckActivity.class);
        //取出 ARRAYLIST 內第一筆物件  抓出ARRAYLIST 裡的第一筆資 料寫法
        //MainActivity.dao.getList().get(0).petName
        it.putExtra("petName",MainActivity.dao.getList().get(0).petName);
        startActivity(it);

    }

}
