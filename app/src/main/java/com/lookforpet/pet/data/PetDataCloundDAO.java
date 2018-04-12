package com.lookforpet.pet.data;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lookforpet.pet.InquireActivity;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Student on 2018/1/23.
 */

public class PetDataCloundDAO implements PetDAOControll {

    public  ArrayList<PetData> mylist;

    //存符合條 件的地方 位何檔都可以存取它
    //public  ArrayList<PetData> okmylist;
    //從FIREBASE 取出資料
    public static  ArrayList<PetData> list;

    //二物件相連
    Context context;
    //FirebaseDatabase
    FirebaseDatabase database;
    //資料送上去的根
    DatabaseReference myRef;
    //類別
    PetData petdata;
    //storage
    FirebaseStorage storage ;
    StorageReference storageRef ;

    //處理圖片
    Uri FileUri;

    //接刪除資料
    public static  ArrayList<PetData> onlydata;



    public PetDataCloundDAO(final Context context)
    {
        this.context=context;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("uploadsimage");

        //預防一開始app沒給值
        mylist = new ArrayList<>();
        //預設先要空間
        //onlydata= new ArrayList<>();
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
        //送基本資料到FIREBASE 全是字串
        //本地URI 字串
        final String  uri=mylist.get(0).uri;
        final String  petName=mylist.get(0).petName;
        final String  petKind=mylist.get(0).petKind;
        final String  petAge=mylist.get(0).petAge;
        final String  petSex=mylist.get(0).petSex;
        final String  petType=mylist.get(0).petType;
        final String  petCity=mylist.get(0).petCity;
        final String  petArea=mylist.get(0).petArea;
        final String  petAddress=mylist.get(0).petAddress;
        final String ownerName=mylist.get(0).ownerName;
        final String ownerTel=mylist.get(0).ownerTel;
        final String ownerLine=mylist.get(0).ownerLine;
        final String ownerEmail=mylist.get(0).ownerEmail;
        final String date=mylist.get(0).date;

        //傳照片到 storageRef
        //字串轉為 URI
        FileUri =Uri.parse(uri);

       // FirebaseStorage storage = FirebaseStorage.getInstance();
        //StorageReference storageRef = storage.getReference("uploadsimage");
       // String str="image/" + String.valueOf(new java.util.Date().getTime());

        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String str="image/" + String.valueOf(new java.util.Date().getTime());

        if(FileUri!=null)
        {
            //取得 the storage  reference
            //StorageReference riversRef = storageRef.child(System.currentTimeMillis()+"."+uri);
            StorageReference riversRef = storageRef.child(str);

            //add file to reference
            riversRef.putFile(FileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                       Toast.makeText(context,"Image uploaded", Toast.LENGTH_SHORT).show();
                       //寫進去 FIREBASE
                    //custom object  每按確定鈕 就建立一筆
                    String loadurl=taskSnapshot.getDownloadUrl().toString();
                    // ----- http
                    Log.d("loadurl---->",loadurl);
                    //
                    petdata=new PetData(loadurl,petName,petKind,petAge,petSex,petType,petCity,petArea,petAddress,ownerName,ownerTel,ownerLine,ownerEmail,date);
                    String key=myRef.child("notes").push().getKey();
                    //這行是跟據路徑  送到FIREBASE 上
                    myRef.child("notes").child(key).setValue(petdata);

                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

        }

//        PetData petdata=new PetData(uri,petName,petKind,petAge,petSex,petType,petCity,petArea,petAddress,ownerName,ownerTel,ownerLine,ownerEmail,date);
//        //在mDatabase ROOT testfirbase-5fb08, 子節點 notes  push是唯一ID  getKey()是把唯一ID值取出來
//        String key=myRef.child("notes").push().getKey();
//        //這行是跟據路徑  送到FIREBASE 上
//        myRef.child("notes").child(key).setValue(petdata);
        //myRef.setValue("Hello, World!")
        //Toast.makeText(context.this,"已上傳",Toast.LENGTH_SHORT).show();

    }


    //  //取回在FIREBASE 資料
    //InquireActivity
    public void getPetData()
    {
        //在這個子節點 notes 設定監聽 只要資料有變就會執行 ValueEventListener 這物件
        //這叫CALLBACK
        myRef.child("notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  dataSnapshot ，取得FIREBASE 裡 notes 節點裡全部內容
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    //FIREBASE 裡取出來的資料
                    PetData note = noteDataSnapshot.getValue(PetData.class);
                    note.id=noteDataSnapshot.getKey().toString();


                    //OK  這邊可以接
                    list.add(note);
                    Log.d("PDAlistsize-->", "" + list.size());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public ArrayList<PetData> getList() {
        return mylist ;
    }

//    public ArrayList<PetData> getOkmylist()
//    {
//        return okmylist ;
//    }

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
