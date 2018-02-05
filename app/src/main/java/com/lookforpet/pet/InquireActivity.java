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

    //
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
    public  String petCity;
    public  String petArea;

    //種類 性別
    public  String petKind;
    public  String petSex;

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_inquire);

        //下拉式選單：種類
        Spinner spkind = (Spinner)findViewById(R.id.petKind);
        ArrayAdapter<CharSequence> choosekind = ArrayAdapter.createFromResource(InquireActivity.this,
                R.array.kind,android.R.layout.simple_spinner_dropdown_item);
        spkind.setAdapter(choosekind);
        spkind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("有沒有來到這裡","有沒有來到這裡");
                petKind=adapterView.getItemAtPosition(i).toString();
                Log.d("有沒有_petKind",petKind);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //下拉式選單：性別
        Spinner spsex = (Spinner)findViewById(R.id.petSex);
        ArrayAdapter<CharSequence> choosesex = ArrayAdapter.createFromResource(InquireActivity.this,
                R.array.sex,android.R.layout.simple_spinner_dropdown_item);
        spsex.setAdapter(choosesex);
        spsex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //點了下拉選項 才會吁叫這方法 沒點下拉 不會吁叫這裡
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                petSex=adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
            Log.d("Citytype---->",""+Citytype[i]);
        }

        //縣市
        ArrayAdapter<String> choosecity = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Citytype);
        spcity.setAdapter(choosecity);
        spcity.setOnItemSelectedListener(selectListener);


        petDataCloundDAO=new PetDataCloundDAO(InquireActivity.this );
        //抓在FIREBASE 資料抓回來
        petDataCloundDAO.getPetData();


        //ListView
        listView =(ListView)findViewById(R.id.listView);


    }

    //呈現要搜尋的資料
    public void showInquire(View v)
    {

        if(petDataCloundDAO.list.size()==0)
        {
            Toast.makeText(InquireActivity.this, "資料正在下載中", Toast.LENGTH_LONG).show();

            return;
        }

        for(int i=0;i<petDataCloundDAO.list.size();i++)
        {
            if( oklist.size()<petDataCloundDAO.list.size())
            {

                //在 arraylist  增加一筆物件
                oklist.add(petDataCloundDAO.list.get(i));
            }

        }

        Log.d("oklist--- ",""+oklist.size());

        showdata();


//        //抓取符合自已要的訊息
//        for(int i=0;i<petDataCloundDAO.list.size();i++)
//        {
//             String fpetKind =petDataCloundDAO.list.get(i).petKind;
//             String fpetSex=petDataCloundDAO.list.get(i).petSex;
//             String fpetCity=petDataCloundDAO.list.get(i).petCity;
//            String fpetArea=petDataCloundDAO.list.get(i).petArea;
//
//            Log.d("fpetKind --",fpetKind);
//            Log.d("fpetSex --",fpetSex);
//            Log.d("fpetCity --",fpetCity);
//            Log.d("fpetArea --",fpetArea);
//
//            //自已輸入的資料
//            Log.d("mpetKind --",petKind);
//            Log.d("mpetSex --",petSex);
//            Log.d("mpetCity --",petCity);
//            Log.d("mpetArea --",petArea);
//
//            if(fpetKind.equals(petKind) && fpetSex.equals(petSex) && fpetCity.equals(petCity) && fpetArea.equals(petArea))
//            {
//                //Inquirelist.add(petDataCloundDAO.list.get(i));
//                if( Inquirelist.size()>0)
//                {
//                   for(int j=0;i<Inquirelist.size();j++)
//                   {
//                       //先arraylist 裡拿掉 物件
//                       Inquirelist.remove(j);
//                   }
//                    //在 Inquirelist  增加物件
//                    Inquirelist.add(petDataCloundDAO.list.get(i));
//
//                }else{
//                    Inquirelist.add(petDataCloundDAO.list.get(i));
//                }
//
//                Log.d("條件符合petArea--",petArea);
//                Log.d("條件符合Inquirelist--",""+Inquirelist.size());
//            }
//
//
//            // 台北市 大同區 貓 雌
////            if(fpetKind.equals(petKind)&&fpetSex.equals(petSex)&&fpetCity.equals(petCity)&&fpetArea.equals(petArea))
////            {
////
////                Inquirelist.add(petDataCloundDAO.list.get(i));
////               //很爛的寫法
//////                if( Inquirelist.size()>0)
//////                {
//////                   for(int j=0;i<Inquirelist.size();j++)
//////                   {
//////                       //先arraylist 裡拿掉 物件
//////                       Inquirelist.remove(j);
//////                   }
//////                    //在 Inquirelist  增加物件
//////                    Inquirelist.add(petDataCloundDAO.list.get(i));
//////
//////                }else{
//////                    Inquirelist.add(petDataCloundDAO.list.get(i));
//////                }
////
////            }
//
//        }

        //Inquirelist
        //Log.d("YYY--->",""+petDataCloundDAO.list.size());
        //Log.d("InquireList",""+Inquirelist.size());

    }

    public void showdata()
    {
        for(int i=0;i<oklist.size();i++)
        {
           String okpetKind=oklist.get(i).petKind;
           String okpetSex=oklist.get(i).petSex;
           String okpetCity=oklist.get(i).petCity;
           String okpetArea=oklist.get(i).petArea;
           String ownername=oklist.get(i).ownerName;

            Log.d("okpetKind",okpetKind);
            Log.d("okpetSex",okpetSex);
            Log.d("okpetCity",okpetCity);
            Log.d("okpetArea",okpetArea);
            Log.d("ownername",ownername);

            Log.d("petKind",petKind);
            Log.d("petSex",petSex);
            Log.d("petCity",petCity);
            Log.d("petArea",petArea);

            // 台北市 大同區 貓 雌--2
            //台北市 大同區   犬 雄--1
            if(okpetKind.equals(petKind)&& okpetSex.equals(petSex) && okpetCity.equals(petCity)&& okpetArea.equals(petArea))
            {

//                Inquirelist.add(petDataCloundDAO.list.get(i));
//               //很爛的寫法
                if( Inquirelist.size()>0)
                {
                   for(int j=0;i<Inquirelist.size();j++)
                   {
                       //先arraylist 裡拿掉 物件
                       Inquirelist.remove(j);
                   }
                    //在 Inquirelist  增加物件
                    Inquirelist.add(petDataCloundDAO.list.get(i));

                }else{
                    Inquirelist.add(petDataCloundDAO.list.get(i));
                }

           }
        }

        Log.d("oklist",""+oklist.size());
        Log.d("Inquirelist",""+Inquirelist.size());

    }


    //spinner第一個下拉類別的監看式
    //縣市  2018 24 增加 取petCity
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener(){
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
            petCity =parent.getItemAtPosition(position).toString();

            //petCity =parent.getItemAtPosition(position).toString();
            Log.d("petCity<------>",""+petCity);

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

            petArea =parent.getItemAtPosition(position).toString();

        }

        public void onNothingSelected(AdapterView<?> arg0){

        }

    };



}
