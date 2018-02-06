package com.lookforpet.pet;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lookforpet.pet.data.PetData;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Student on 2018/1/9.
 */

public class Myadpter extends BaseAdapter {

    ArrayList<PetData> arrayList;

    Context context;

    Myadpter(Context context)
    {
        this.context=context;


    }

    @Override
    public int getCount() {
        return arrayList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //一開始 v是null
    //滑動手機 吁叫 getView 此方法
    //position 就是看  getCount 有多少值 5個 0 1 2 3 4
    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        ViewHolder viewHolder;
        //程式一開始跑 v 會是 null 把要抓的元件全抓起來 優化程式碼
       if(v==null) {
           //context 二元件連接點
           LayoutInflater inflater = LayoutInflater.from(context);
           //抓出 layout
           v = inflater.inflate(R.layout.myitem, null);
           //建立物件 存元件 下次直接取出來 就不必一直 findViewById
           viewHolder =new ViewHolder();
           //這個layout 再抓出 textview 元件
           viewHolder.tv =v.findViewById(R.id.petName);
           viewHolder.tv3 =v.findViewById(R.id.systemDate);
           viewHolder.image=v.findViewById(R.id.imageView);

       }
       else
       {
           //直接抓存在類別裡的元件，不必再 findViewById
           //getTag 在資源回收桶取出東西
           viewHolder = (ViewHolder) v.getTag();
       }

       //取出值
//        viewHolder.tv.setText(arrayList.get(position).get("city").toString());
//        viewHolder.tv3.setText(arrayList.get(position).get("code").toString());
//        //viewHolder. image.setImageResource((Integer) arrayList.get(position).get("image"));

        viewHolder.tv.setText(arrayList.get(position).petName.toString());
        viewHolder.tv3.setText(arrayList.get(position).date.toString());
//        String pic=arrayList.get(position).uri.toString();
//        Uri FileUri =Uri.parse(pic);
//        ContentResolver cr = this.getContentResolver();
//        try {
//            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(FileUri));//由抽象資料接口轉換圖檔路徑為Bitmap
//            Log.d("bitmap", bitmap.toString());//寫log 若寫這個過去 只需要下面二行程式碼相同
//            viewHolder.image.setImageBitmap(bitmap);
//        } catch (FileNotFoundException e) {
//            Log.e("Exception", e.getMessage(), e);
//        }


        //viewHolder. image.setImageResource((Integer) arrayList.get(position).get("image"));

        //元件設tab
         v.setTag(viewHolder);
        return v;

    }
    //這類別功能 就是存元件位址 讓 getView不必抓元件位置 讓浪費時間
    static class ViewHolder
    {
        TextView tv;
        TextView tv3;
        ImageView image;

    }

}
