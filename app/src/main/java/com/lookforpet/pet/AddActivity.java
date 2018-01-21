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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    TextView tv;
    Button bt1;

    private File tempFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_add);
        this.tempFile = new File("/sdcard/a.jpg");
        //找尋Button按鈕
        bt1 = (Button)findViewById(R.id.petButton);
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



        //下拉式選單：種類
        Spinner spkind = (Spinner)findViewById(R.id.petKind);
        ArrayAdapter<CharSequence> choosekind = ArrayAdapter.createFromResource(AddActivity.this,
                R.array.kind,android.R.layout.simple_spinner_dropdown_item);
        spkind.setAdapter(choosekind);
        //下拉式選單：性別
        Spinner spsex = (Spinner)findViewById(R.id.petSex);
        ArrayAdapter<CharSequence> choosesex = ArrayAdapter.createFromResource(AddActivity.this,
                R.array.sex,android.R.layout.simple_spinner_dropdown_item);
        spsex.setAdapter(choosesex);
        //下拉式選單：體型
        Spinner sptype = (Spinner)findViewById(R.id.petType);
        ArrayAdapter<CharSequence> choosetype = ArrayAdapter.createFromResource(AddActivity.this,
                R.array.type,android.R.layout.simple_spinner_dropdown_item);
        sptype.setAdapter(choosetype);


        Calendar mCal = Calendar.getInstance();
        CharSequence s = DateFormat.format("yyyy-MM-dd", mCal.getTime());    // kk:24小時制, hh:12小時制
        tv=(TextView)findViewById(R.id.systemData);
        tv.setText(String.valueOf(s.toString()));



    }
    //取得相片後返回的監聽式
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageView = (ImageView) findViewById(R.id.petImage);
        //當使用者按下確定後
        if (resultCode == RESULT_OK) {

            // 設定到ImageView
            imageView.setImageDrawable(Drawable.createFromPath(tempFile.getAbsolutePath()));

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public void clickOk(View v)
    {
        Intent it=new Intent(AddActivity.this,CheckActivity.class);
        startActivity(it);
    }
}
