package com.lookforpet.pet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    TextView data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_add);
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
}
