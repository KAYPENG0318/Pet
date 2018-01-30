package com.lookforpet.pet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.lookforpet.pet.data.PetData;
import com.lookforpet.pet.data.PetDataCloundDAO;

import java.util.ArrayList;

public class InquireActivity extends AppCompatActivity {
    TextView txtpetName;
    TextView txtpetKind;
    TextView txtpetAge;
    PetDataCloundDAO petDataCloundDAO;
    public static ArrayList<PetData> Inquirelist=new ArrayList<>();

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

        //下拉式選單：性別
        Spinner spsex = (Spinner)findViewById(R.id.petSex);
        ArrayAdapter<CharSequence> choosesex = ArrayAdapter.createFromResource(InquireActivity.this,
                R.array.sex,android.R.layout.simple_spinner_dropdown_item);
        spsex.setAdapter(choosesex);


        showInquire();


    }

    public void showInquire()
    {

        txtpetName=(TextView)findViewById(R.id.petName);

        txtpetKind=(TextView)findViewById(R.id.petKind);

        txtpetAge=(TextView)findViewById(R.id.petAge);

        //可以在這裡 放入ARRAYLIST 讓這裡的ARRAYLIST 接收對方的值進來
        petDataCloundDAO=new PetDataCloundDAO(InquireActivity.this);

        petDataCloundDAO.Inquire();

        //Log.d("--petDataCloundDAO",""+petDataCloundDAO.getList().size());

        //ResponsePetData responsePetData = new ResponsePetData();

        //petDataCloundDAO.getOkmylist;

        // Log.d("--petDataCloundDAO",""+Inquirelist.size());

        //
//        if(petDataCloundDAO.okmylist.size()!=0)
//        {
//            // TEST用 取出是要的物件再抓出值 一直取出是 0
//            for (int i = 0; i < petDataCloundDAO.okmylist.size(); i++) {
//                String name = petDataCloundDAO.okmylist.get(i).petName;
//
//            }
//
//        }


        //  Log.d("DAOokmylist取出來的值是---:", PetDataCloundDAO.responsePetData.getPetName());

        //txtpetName.setText(PetDataCloundDAO.responsePetData.petName);
//
        // txtpetKind.setText(petDataCloundDAO.getOkmylist().get(0).petName.toString());
//
//        txtpetAge.setText(responsePetData.petAge.toString());


    }


//    public void gohome(View v)
//    {
//        Intent it =new Intent(InquireActivity.this,MainActivity.class);
//        startActivity(it);
//        finish();
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        finish();
//    }
}
