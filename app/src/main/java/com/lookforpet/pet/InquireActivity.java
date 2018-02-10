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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.lookforpet.pet.data.PetDAO;
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
    //private InterstitialAd mInterstitialAd;
    TextView txtpetName;
    TextView txtpetKind;
    TextView txtpetAge;
    PetDataCloundDAO petDataCloundDAO;
    public ArrayList<PetData> oklist=new ArrayList<>();

    //符合資料放這裡
    public  ArrayList<PetData> Inquirelist=new ArrayList<>();

    //很不好的寫法 放符合資料 到DetailActivity
    final public static PetDAO dao= new PetDAO();


    //TEST
    ImageListAdapter adapter;

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
        //插頁式廣告
        //loadInterstitialAd();

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
                //Log.d("有沒有來到這裡", "有沒有來到這裡");
                spetKind = adapterView.getItemAtPosition(i).toString();
                //Log.d("有沒有_petKind", spetKind);

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
            //Log.d("Citytype---->", "" + Citytype[i]);
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

    //插頁式廣告
//    private void loadInterstitialAd() {
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        mInterstitialAd.setAdListener(new AdListener() {
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//                //Toast.makeText(InquireActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
//                if(mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                }
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//                super.onAdFailedToLoad(i);
//                //Toast.makeText(InquireActivity.this, "onAdFailedToLoad()", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mInterstitialAd.loadAd(adRequest);
//    }

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
            //接從FIREBASE 抓出來的值
            oklist.add(petDataCloundDAO.list.get(i));

        }

        //Log.d("oklist--- ",""+oklist.size());

        showdata();


    }

    //清掉之前 選的資料  ARRAYLIST 裡面的資料 重新來一次
    public void showdata()
    {

     //Log.d("showdata","showdata");
        Inquirelist.clear();
       //硬寫不是很好
        dao.getList().clear();

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

//            Log.d("okpetKind", okpetKind);
//            Log.d("okpetSex", okpetSex);
//            Log.d("okpetCity", okpetCity);
//            Log.d("okpetArea", okpetArea);
//            Log.d("ownername", ownername);
//
//            Log.d("petKind",petKind);
//            Log.d("petSex",petSex);
//            Log.d("petCity",petCity);
//            Log.d("petArea",petArea);

            //---------------------------------------------正確的資料------------------------------------------------
            //比對搜尋條件相同 放入ARRAYLIST
            // 台北市 大同區 貓 雌--2
            //台北市 大同區   犬 雄--1

            if (okpetKind.equals(petKind) && okpetSex.equals(petSex) && okpetArea.equals(petArea)) {
                //FIREBASE 下載下來的資料
                //這裡是KEY 它是FIREBASE 下載下來 不是本地端的PetData類別資料
                //TEST 都有跑出來   ****這裡要確定 一定每次都有跑出來 有東西 才可以抓值***
                Inquirelist.add(oklist.get(i));
               // Log.d("oklist",""+oklist.size());
                //確定有東西  跑出來~~
                //Log.d("Inquirelist",""+Inquirelist.size());
               //硬寫不是很好
                dao.add(new PetData(oklist.get(i).uri,oklist.get(i).petName,oklist.get(i).petKind,oklist.get(i).petAge,oklist.get(i).petSex, oklist.get(i).petType,oklist.get(i).petCity,oklist.get(i).petArea,oklist.get(i).petAddress,oklist.get(i).ownerName,oklist.get(i).ownerTel,oklist.get(i).ownerLine,oklist.get(i).ownerEmail,oklist.get(i).date));
              // Log.d("圖片--->",oklist.get(i).uri.toString());


//                if(Inquirelist.size()==0)
//                {
//                    Toast.makeText(InquireActivity.this, "資料正在下載中", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                //FIREBASE 裡資料放在本地端
//                dao.add(new PetData(Inquirelist.get(i).uri,Inquirelist.get(i).petName,Inquirelist.get(i).petKind,Inquirelist.get(i).petAge,Inquirelist.get(i).petSex, Inquirelist.get(i).petType,Inquirelist.get(i).petCity,Inquirelist.get(i).petArea,Inquirelist.get(i).petAddress,Inquirelist.get(i).ownerName,Inquirelist.get(i).ownerTel,Inquirelist.get(i).ownerLine,Inquirelist.get(i).ownerEmail,Inquirelist.get(i).date));

            }


        }

        //listView 呈現畫面 它等於接上FIREBASE 的物件
        myadpter.arrayList=Inquirelist;

        //Log.d("log",""+Inquirelist.get(0));

        //TEST 它也可以用喔~
        //adapter= new ImageListAdapter(InquireActivity.this,R.layout.myitem,  Inquirelist);
        //listView.setAdapter(adapter);

       listView.setAdapter(myadpter);

       //很奇怪的寫法 硬寫的   Inquirelist已是 物件 給予dao.mylist 為了取出 放在 ArrayList內的PetDATA物件
        //REFERENCE FIREBASE
        //TEST 是不行的
       // MainActivity.dao.mylist =Inquirelist;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                // 利用索引值取得點擊的項目內容。
                         // 按的是哪一個VIEW arg2
           // Log.d("index",""+index);
        Intent it = new Intent(InquireActivity.this, DetailActivity.class);
             //取出 ARRAYLIST 內第一筆物件  抓出ARRAYLIST 裡的第一筆資 料寫法
       // it.putExtra("petName",dao.getList().get(index).petName);
        it.putExtra("picUri",dao.getList().get(index).uri);
        String ssuri =dao.getList().get(index).uri;
        //Log.d("ssuri",ssuri);
        //Log.d("index-->",""+index);
        startActivity(it);

            }
        });



//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapter, View arg1, int arg2,
//                                    long arg3) {
////               // 按的是哪一個VIEW arg2
////               // Log.d("arg2",""+arg2);
//        Intent it = new Intent(InquireActivity.this, DetailActivity.class);
//////                //取出 ARRAYLIST 內第一筆物件  抓出ARRAYLIST 裡的第一筆資 料寫法
//        it.putExtra("petName",dao.getList().get(arg2).petName);
//        //it.putExtra("picUri",dao.getList().get(arg2).uri);
//        String ssuri =dao.getList().get(arg2).uri;
//        Log.d("ssuri",ssuri);
//        Log.d("arg2-->",""+arg2);
//        startActivity(it);
//
//            }
//        });


    }


    //spinner第一個下拉類別的監看式
    //縣市  2018 24 增加 取petCity
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener(){
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
            spetCity =parent.getItemAtPosition(position).toString();

            //petCity =parent.getItemAtPosition(position).toString();
            //Log.d("petCity<------>",""+spetCity);

            //讀取第一個下拉選單是選擇第幾個
            ArrayList<Map<String,String>> list = citys[position].AreaList;
            //String value = list.get(position).get(position);
            // Log.d("<----value--->",value);

            String[] listname = new String[list.size()];

            for(int i=0;i<list.size();i++)
            {
                listname[i]=list.get(i).get("AreaName");
               // Log.d("AreaName--->  "," "+listname[i]);
            }
            //Log.d("LIST:" ,listname.toString());


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
