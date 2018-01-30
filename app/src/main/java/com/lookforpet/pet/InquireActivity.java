package com.lookforpet.pet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class InquireActivity extends AppCompatActivity {

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
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        finish();
//    }
}
