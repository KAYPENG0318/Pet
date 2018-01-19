package com.lookforpet.pet;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.FileNotFoundException;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    TextView data;
    Button bt1;
    ImageView imv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_add);

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
                //取得相片後返回本畫面
                startActivityForResult(intent, 1);
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
        data=(TextView)findViewById(R.id.systemData);
        data.setText(String.valueOf(s.toString()));

    }
    //取得相片後返回的監聽式
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //當使用者按下確定後
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();//取得圖檔的路徑位置
            //Log.e("uri", uri.toString());//寫log
            //抽象資料的接口
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));//由抽象資料接口轉換圖檔路徑為Bitmap
                imv = (ImageView) findViewById(R.id.petImage);//取得圖片控制項ImageView
                imv.setImageBitmap(bitmap);// 將Bitmap設定到ImageView
            } catch (FileNotFoundException e) {
               // Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
