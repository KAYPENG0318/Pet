package com.lookforpet.pet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.lookforpet.pet.data.PetDAO;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //插頁式廣告
    private InterstitialAd mInterstitialAd;
    //橫幅廣告
    private static final String TAG = "MainActivity";
    private AdView mAdView;
    //靜態變數
    final public static PetDAO dao= new PetDAO();

    //Button mMyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_main);
        //橫幅廣告
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //插頁式廣告
        mInterstitialAd = new InterstitialAd(MainActivity.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void buttonAdd(View v)
    {
        Intent it=new Intent(MainActivity.this,AddActivity.class);
        startActivity(it);
        //新增 20180130
       // finish();
    }
    public void buttonInquire(View v)
    {




        Intent it=new Intent(MainActivity.this,InquireActivity.class);
        startActivity(it);

        //插頁式廣告
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            Log.d("TAG", "AD SHOW!");
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        //新增 20180130
       // finish();


    }
}
