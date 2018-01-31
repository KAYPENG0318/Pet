package com.lookforpet.pet.data;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lookforpet.pet.InquireActivity;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/23.
 */

public class PetDataCloundDAO implements PetDAOControll {


    public  ArrayList<PetData> mylist;

    //存符合條 件的地方 位何檔都可以存取它
    public  ArrayList<PetData> okmylist;

    //從FIREBASE 取出資料
    public static  ArrayList<PetData> list;

    //二物件相連
    Context context;
    //FirebaseDatabase
    FirebaseDatabase database;
    //資料送上去的根
    DatabaseReference myRef;

    //在firebase 產生id
    private String userId;

    public PetDataCloundDAO(final Context context)
    {
        this.context=context;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        //預防一開始app沒給值
        mylist = new ArrayList<>();
        //預設先要空間
        okmylist= new ArrayList<>();
        list=new ArrayList<>();

    }

   // 別的檔案加類別 進來
    @Override
    public boolean add(PetData p) {
        //學生物件 加入到  ArrayList裡
        mylist.add(p);
        //存檔
        saveFile();
        return true;
    }


    //Write a message to the database
    private void saveFile()
    {
        String  petName=mylist.get(0).petName;
        String  petKind=mylist.get(0).petKind;
        String  petAge=mylist.get(0).petAge;
        String  petSex=mylist.get(0).petSex;
        String  petType=mylist.get(0).petType;
        String  petCity=mylist.get(0).petCity;
        String  petArea=mylist.get(0).petArea;
        String  petAddress=mylist.get(0).petAddress;
        String ownerName=mylist.get(0).ownerName;
        String ownerTel=mylist.get(0).ownerTel;
        String ownerLine=mylist.get(0).ownerLine;
        String ownerEmail=mylist.get(0).ownerEmail;
        String date=mylist.get(0).date;





        //custom object  每按確定鈕 就建立一筆
        PetData petdata=new PetData(petName,petKind,petAge,petSex,petType,petCity,petArea,petAddress,ownerName,ownerTel,ownerLine,ownerEmail,date);
        //在mDatabase ROOT testfirbase-5fb08, 子節點 notes  push是唯一ID  getKey()是把唯一ID值取出來讓人類看
        String key=myRef.child("notes").push().getKey();
        //這行是跟據路徑  送到FIREBASE 上
        myRef.child("notes").child(key).setValue(petdata);
    }

    //取出資料
    public void Inquire()
    {
        Log.d("print---","print");
        //在這個子節點 notes 設定監聽 只要資料有變就會執行 ValueEventListener 這物件
        //這叫CALLBACK
        myRef.child("notes").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  dataSnapshot ，取得FIREBASE 裡 notes 節點裡全部內容
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    //取出來是 物件，所以要給裝這物件合的來的箱子
                    PetData note = noteDataSnapshot.getValue(PetData.class);
                    //每一個物件取出來 放入 ARRAYLIST 內
                    list.add(note);
                    Log.d("InquireActivity--->",""+ list.size());

                }

                //取出ARRAYLIST 內的物件，物件再取出裡面的值
                for (int i = 0; i < list.size(); i++) {
                    String name = list.get(i).petName;
                    Log.d("我取出來的值是---:", name);

                    //查詢條件寫在這裡
                    if (name.equals("Namexxxx")) {
                        // 抓出符合條件的 物件放進去okmylist內
                        //list 這個都是參考到 記憶體的值
                        //???  有可能是這裡錯 要放 PetData
//                        String nname=list.get(i).petName;
//                        String kind=list.get(i).petKind;
//                        String age=list.get(i).petAge;

                       // Log.d("我取出來的值是---:", petdata);
                        //TEST 用
                        okmylist.add(list.get(i));
                        //Inquirelist 的ARRAYLIST 接
                        InquireActivity.Inquirelist.add(list.get(i));

                        Log.d("InquireActivity--->",""+ InquireActivity.Inquirelist.size());

                    }
                }
              //TEST 用
              for(int i=0;i<okmylist.size();i++)
              {
                  String name=okmylist.get(i).petName;
                  String kind=okmylist.get(i).petKind;
                  String petage=okmylist.get(i).petAge;
                  Log.d("有沒有值---:", name);
                  Log.d("大小大小",""+okmylist.size() );
                  Log.d("有沒有值---:", kind);
                  Log.d("有沒有值---:",petage);
              }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }  );
    }

    @Override
    public ArrayList<PetData> getList() {
        return mylist ;
    }

    public ArrayList<PetData> getOkmylist()
    {
        return okmylist ;
    }

    //TEST
    public ArrayList<PetData> getlist()
    {
        return list ;
    }


    @Override
    public PetData getStudent(String petName) {
        for(PetData s:mylist)
        {
            if(s.petName.equals(petName))
            {
                return s;
            }
        }
        return  null;

    }

    @Override
    public boolean update(PetData s) {
        //把已改好的 資料 載入至 ram中
        for(PetData t:mylist)
        {
            if(t.petName.equals(s))
            {
                t.petName=s.petName;
                t.petKind=s.petKind;
                t.petAge=s.petAge;
                saveFile();
                return true;
            }
        }
        return  false;
    }

    //年月日比對 超過二個月 資料刪掉
    @Override
    public boolean delete(String petName) {

        for (int i=0;i<mylist.size();i++)
        {
            if (mylist.get(i).petName.equals(petName))
            {
                mylist.remove(i);
                saveFile();
                return true;
            }
        }

        return false;
    }
}
