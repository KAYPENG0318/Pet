package com.lookforpet.pet.data;

import android.content.Context;
import android.util.Log;

import com.example.student.testpet.InquireActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/23.
 */

public class PetDataCloundDAO implements PetDAOControll {

    //箱子
    public  ArrayList<PetData> mylist=new ArrayList<>();

    //存符合條 件的地方 位何檔都可以存取它
    public  ArrayList<PetData> okmylist=new ArrayList<>();

    //從FIREBASE 取出資料
    public static  ArrayList<PetData> list=new ArrayList<>();

    //二物件相連
    Context context;
    //FirebaseDatabase
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    //資料送上去的根
    DatabaseReference myRef=database.getReference();

    //在firebase 產生id
    private String userId;

    public PetDataCloundDAO(final Context context)
    {
        this.context=context;
        //atabase = FirebaseDatabase.getInstance();
        //根叫 scores
        //myRef = database.getReference();
        //預防一開始app沒給值
       // mylist = new ArrayList<>();
        //預設先要空間
       // okmylist= new ArrayList<>();
       // list=new ArrayList<>();

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
        String _petName=mylist.get(0).petName;
        String _petKind=mylist.get(0).petKind;
        String _petAge=mylist.get(0).petAge;

        //custom object  每按確定鈕 就建立一筆
        PetData petdata=new PetData(_petName,_petKind,_petAge);
        //在mDatabase ROOT testfirbase-5fb08, 子節點 notes  push是唯一ID  getKey()是把唯一ID值取出來讓人類看
        String key=myRef.child("notes").push().getKey();
        //這行是跟據路徑  給值
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
                        okmylist.add(list.get(i));
                        InquireActivity.Inquirelist.add(list.get(i));

                        Log.d("InquireActivity--->",""+ InquireActivity.Inquirelist.size());

                    }
                }

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
