package com.lookforpet.pet;

import android.*;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.lookforpet.pet.data.PetData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class AddActivity extends AppCompatActivity {

    EditText edpetName,edpetAge;
    EditText edownerName,edownerTel,edownerLine,edownerEmail;
    Spinner spkind,spsex,sptype;
    String petSex;
    String petType;
    String petName;
    String petKind;
    String petAge;
    String petCity;
    String petArea;
    String petAddress;
    String ownerName,ownerTel,ownerLine,ownerEmail;
    String Date;
    Spinner spcity,spcity2;
    City[] citys;
    Uri FileUri;
    String suri;
    Bitmap bitmap;
    private File tempFile;
    Button btpic;
    ImageView imagepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_add);

        //spinner抓取JSON全台縣市與鄉鎮區域
        spcity=(Spinner)findViewById(R.id.petCity);
        spcity2=(Spinner)findViewById(R.id.petArea);
        InputStream is = getResources().openRawResource(R.raw.mydata);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String str;
        try {
            str = br.readLine();
            sb.append(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str1=sb.toString();
        Gson gson=new Gson();
        citys=gson.fromJson(str1,City[].class);
        String[] Citytype = new String[citys.length];
        for(int i=0;i<citys.length;i++)
        {
            Citytype[i]=citys[i].CityName;
        }
        ArrayAdapter<String> choosecity = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Citytype);
        spcity.setAdapter(choosecity);
        spcity.setOnItemSelectedListener(selectListener);


        //抓取手機圖片
        this.tempFile = new File("/sdcard/a.jpg");
        //找尋Button按鈕
        btpic = (Button)findViewById(R.id.petButton);
        btpic.setText("選擇圖片");//設定按鈕內文字

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
//

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
//
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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


    public void petButton(View v)
    {

        int permission = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {WRITE_EXTERNAL_STORAGE,
                            READ_EXTERNAL_STORAGE},
                    123
            );
        }
        else
        {
            readPic();
        }
    }
    //
    private void readPic()
    {
        this.tempFile = new File(getExternalFilesDir("PHOTO"), "myphoto.jpg");
        //找尋Button按鈕

        btpic.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //開啟Pictures畫面Type設定為image
                intent.setType("image/*");
                //使用Intent.ACTION_GET_CONTENT這個Action  //會開啟選取圖檔視窗讓您選取手機內圖檔
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                intent.putExtra("crop", true);// crop=true 有這句才能叫出裁剪頁面.
                intent.putExtra("aspectX", 1);// 这兩項為裁剪框的比例.
                intent.putExtra("aspectY", 1);// x:y=1:1
                intent.putExtra("return-data", true);
                intent.putExtra("output", Uri.fromFile(tempFile));
                intent.putExtra("outputFormat", "JPEG");//返回格式


                //取得相片後返回本畫面
                startActivityForResult(Intent.createChooser(intent,"選擇圖片"),456);
            }
        });

    }
    //取得相片後返回的監聽式
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 456) {
            //當使用者按下確定後
            if (resultCode == RESULT_OK) {
                // 設定到ImageView
                //這邊抓URL 送到 CheckActivity
                //本機路徑
                FileUri = data.getData();//取得圖檔的路徑位置

                //uri 轉字串
                suri=FileUri.toString();

                Log.d("uri", FileUri.toString());//寫log
                //抽象資料的接口
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(FileUri));//由抽象資料接口轉換圖檔路徑為Bitmap
                    imagepic = (ImageView) findViewById(R.id.petImage);//取得圖片控制項ImageView
                    imagepic.setImageBitmap(bitmap);// 將Bitmap設定到ImageView
                } catch (FileNotFoundException e) {
                    Log.e("Exception", e.getMessage(), e);
                }
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123)
        {

            if (grantResults.length > 0
                    && grantResults[0] == PERMISSION_GRANTED) {
                //取得權限，進行檔案存取

                readPic();
            } else {
                //使用者拒絕權限，停用檔案存取功能
            }
            return;
        }
    }
    //填好資料送到確定頁
    public void clickOk(View v)
    {
        if(suri==null)
        {
            Toast.makeText(AddActivity.this,"請選擇毛小孩照片",Toast.LENGTH_LONG).show();
            return;

        }

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
            MainActivity.dao.add(new PetData(suri,petName,petKind,petAge,petSex,petType,petCity,petArea,petAddress,ownerName,ownerTel,ownerLine,ownerEmail,Date));

        }else{
            //有資料在 listarray裡了
            MainActivity.dao.add(new PetData(suri,petName,petKind,petAge,petSex,petType,petCity,petArea,petAddress,ownerName,ownerTel,ownerLine,ownerEmail,Date));
        }

        Log.d("","AddActivity----"+MainActivity.dao.getList().size());

        //資料送到  CheckActivity
        Intent it = new Intent(AddActivity.this, CheckActivity.class);
        //取出 ARRAYLIST 內第一筆物件  抓出ARRAYLIST 裡的第一筆資 料寫法
        //MainActivity.dao.getList().get(0).petName
        it.putExtra("petName",MainActivity.dao.getList().get(0).petName);

        startActivity(it);

    }
    //spinner第一個下拉類別的監看式
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener(){
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
            petCity =parent.getItemAtPosition(position).toString();

            //讀取第一個下拉選單是選擇第幾個
            ArrayList<Map<String,String>> list = citys[position].AreaList;
            String[] listname = new String[list.size()];

            for(int i=0;i<list.size();i++)
            {
                listname[i]=list.get(i).get("AreaName");
            }
            Log.d("LIST:" ,listname.toString());
            ArrayAdapter<String> choosearea = new ArrayAdapter<String>(AddActivity.this,android.R.layout.simple_spinner_item,listname);
            spcity2.setAdapter(choosearea);
            spcity2.setOnItemSelectedListener(selectAR);
        }

        public void onNothingSelected(AdapterView<?> arg0){

        }

    };

    // 行政區
    //201824 增加取petArea
    private AdapterView.OnItemSelectedListener selectAR = new AdapterView.OnItemSelectedListener(){
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
            //OK
//            Log.d("position",""+position);
            petArea =parent.getItemAtPosition(position).toString();
            Log.d("petArea<------>",""+petArea);

        }

        public void onNothingSelected(AdapterView<?> arg0){

        }

    };

}
