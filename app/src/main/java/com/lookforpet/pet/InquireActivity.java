package com.lookforpet.pet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lookforpet.pet.data.PetData;
import com.lookforpet.pet.data.PetDataCloundDAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class InquireActivity extends AppCompatActivity {
    TextView txtpetName;
    TextView txtpetKind;
    TextView txtpetAge;
    PetDataCloundDAO petDataCloundDAO;

    public ArrayList<PetData> oklist=new ArrayList<>();

    //符合資料放這裡
    public  ArrayList<PetData> Inquirelist=new ArrayList<>();

    //LAYOUT 長的資料填完整
    //縣市 行政區
    City[] citys;
    private File tempFile;
    Spinner  spcity;
    Spinner  spcity2;

    //USER 沒輸入時 預設值
    public  String spetCity;
    public  String spetArea;

    //種類 性別
    public  String spetKind;
    public  String spetSex;

    Myadpter myadpter;

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_inquire);

        //下拉式選單：種類
        Spinner spkind = (Spinner) findViewById(R.id.petKind);
        ArrayAdapter<CharSequence> choosekind = ArrayAdapter.createFromResource(InquireActivity.this,
                R.array.kind, android.R.layout.simple_spinner_dropdown_item);
        spkind.setAdapter(choosekind);
        spkind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("有沒有來到這裡", "有沒有來到這裡");
                spetKind = adapterView.getItemAtPosition(i).toString();
                Log.d("有沒有_petKind", spetKind);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //下拉式選單：性別
        Spinner spsex = (Spinner) findViewById(R.id.petSex);
        ArrayAdapter<CharSequence> choosesex = ArrayAdapter.createFromResource(InquireActivity.this,
                R.array.sex, android.R.layout.simple_spinner_dropdown_item);
        spsex.setAdapter(choosesex);
        spsex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //點了下拉選項 才會吁叫這方法 沒點下拉 不會吁叫這裡
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spetSex = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //spinner抓取JSON全台縣市與鄉鎮區域
        spcity = (Spinner) findViewById(R.id.petCity);
        spcity2 = (Spinner) findViewById(R.id.petArea);

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
        String str1 = sb.toString();
        Gson gson = new Gson();
        citys = gson.fromJson(str1, City[].class);
        String[] Citytype = new String[citys.length];
        for (int i = 0; i < citys.length; i++) {
            Citytype[i] = citys[i].CityName;
            Log.d("Citytype---->", "" + Citytype[i]);
        }

        //縣市
        ArrayAdapter<String> choosecity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Citytype);
        spcity.setAdapter(choosecity);
        spcity.setOnItemSelectedListener(selectListener);


        petDataCloundDAO = new PetDataCloundDAO(InquireActivity.this);
        //抓在FIREBASE 資料抓回來
        petDataCloundDAO.getPetData();


        //ListView
        listView = (ListView) findViewById(R.id.listView);

        myadpter = new Myadpter(InquireActivity.this);


    }
    //呈現要搜尋的資料
    public void showInquire(View v)
    {

        if(petDataCloundDAO.list.size()==0)
        {
            Toast.makeText(InquireActivity.this, "資料正在下載中", Toast.LENGTH_LONG).show();

            return;
        }

          oklist.clear();

        for(int i=0;i<petDataCloundDAO.list.size();i++)
        {

            oklist.add(petDataCloundDAO.list.get(i));

        }

        Log.d("oklist--- ",""+oklist.size());

        showdata();


    }

    //清掉之前 選的資料  ARRAYLIST 裡面的資料 重新來一次
    public void showdata()
    {

     Log.d("showdata","showdata");
        Inquirelist.clear();

//       //從FIREBASE 抓取全部資料過來
        for(int i=0;i<oklist.size();i++) {
            String okpetKind = oklist.get(i).petKind.trim();
            String okpetSex = oklist.get(i).petSex.trim();
            String okpetCity = oklist.get(i).petCity.trim();
            String okpetArea = oklist.get(i).petArea.trim();
            String ownername = oklist.get(i).ownerName.trim();

            //spinner
            String petKind = spetKind.trim();
            String petSex=spetSex.trim();
            String petCity=spetCity.trim();
            String petArea=spetArea.trim();


            Log.d("okpetKind", okpetKind);
            Log.d("okpetSex", okpetSex);
            Log.d("okpetCity", okpetCity);
            Log.d("okpetArea", okpetArea);
            Log.d("ownername", ownername);

            Log.d("petKind",petKind);
            Log.d("petSex",petSex);
            Log.d("petCity",petCity);
            Log.d("petArea",petArea);

            //---------------------------------------------正確的資料------------------------------------------------
            //比對搜尋條件相同 放入ARRAYLIST
            // 台北市 大同區 貓 雌--2
            //台北市 大同區   犬 雄--1

            if (okpetKind.equals(petKind) && okpetSex.equals(petSex) && okpetArea.equals(petArea)) {

                Inquirelist.add(oklist.get(i));
//                Log.d("Inquirelist",""+Inquirelist.size());

            }

                Log.d("Inquirelist",""+Inquirelist.size());

            }


            //listView 呈現畫面
        myadpter.arrayList=Inquirelist;
        listView.setAdapter(myadpter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
               // 按的是哪一個VIEW arg2
                Log.d("arg2",""+arg2);
                Intent it = new Intent(InquireActivity.this, DetailActivity.class);
                //取出 ARRAYLIST 內第一筆物件  抓出ARRAYLIST 裡的第一筆資 料寫法
                //MainActivity.dao.getList().get(0).petName
                it.putExtra("petName",Inquirelist.get(arg2).petName);
                it.putExtra("petKind",Inquirelist.get(arg2).petKind);
                it.putExtra("petAge",Inquirelist.get(arg2).petAge);
                it.putExtra("petSex",Inquirelist.get(arg2).petSex);
                it.putExtra("petType",Inquirelist.get(arg2).petType);
                it.putExtra("petCity",Inquirelist.get(arg2).petCity);
                it.putExtra("petArea",Inquirelist.get(arg2).petArea);
                it.putExtra("petAddress",Inquirelist.get(arg2).petAddress);
                it.putExtra("ownerName",Inquirelist.get(arg2).ownerName);
                it.putExtra("ownerTel",Inquirelist.get(arg2).ownerTel);
                it.putExtra("ownerLine",Inquirelist.get(arg2).ownerLine);
                it.putExtra("ownerEmail",Inquirelist.get(arg2).ownerEmail);
                it.putExtra("date",Inquirelist.get(arg2).date);

                //暫時寫法 應該要把 bitmap 傳到 PetData 內
                //it.putExtra("BitmapImage", bitmap);
                startActivity(it);

            }
        });


    }


    //spinner第一個下拉類別的監看式
    //縣市  2018 24 增加 取petCity
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener(){
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
            spetCity =parent.getItemAtPosition(position).toString();

            //petCity =parent.getItemAtPosition(position).toString();
            Log.d("petCity<------>",""+spetCity);

            //讀取第一個下拉選單是選擇第幾個
            ArrayList<Map<String,String>> list = citys[position].AreaList;
            //String value = list.get(position).get(position);
            // Log.d("<----value--->",value);

            String[] listname = new String[list.size()];

            for(int i=0;i<list.size();i++)
            {
                listname[i]=list.get(i).get("AreaName");
                Log.d("AreaName--->  "," "+listname[i]);
            }
            Log.d("LIST:" ,listname.toString());


            ArrayAdapter<String> choosearea = new ArrayAdapter<String>(InquireActivity.this,android.R.layout.simple_spinner_item,listname);
            //行政區
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
            spetArea =parent.getItemAtPosition(position).toString();

        }

        public void onNothingSelected(AdapterView<?> arg0){

        }

    };



}
