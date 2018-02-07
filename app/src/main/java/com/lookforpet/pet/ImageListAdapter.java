package com.lookforpet.pet;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lookforpet.pet.data.PetData;

import java.util.List;

/**
 * Created by Jiahui on 2018/2/8.
 */

public class ImageListAdapter extends ArrayAdapter<PetData> {
    private Activity context;
    private int  resource;
    public List<PetData> imagelist;
    public ImageListAdapter(@NonNull Activity context, int resource, @NonNull List<PetData> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        imagelist=objects;
    }


    @Override
    public View getView(final int position, View v, ViewGroup parent) {


        Myadpter.ViewHolder viewHolder;
        //程式一開始跑 v 會是 null 把要抓的元件全抓起來 優化程式碼
        if(v==null) {
            //context 二元件連接點
            LayoutInflater inflater = LayoutInflater.from(context);
            //抓出 layout
            v = inflater.inflate(R.layout.myitem, null);
            //建立物件 存元件 下次直接取出來 就不必一直 findViewById
            viewHolder =new Myadpter.ViewHolder();
            //這個layout 再抓出 textview 元件
            viewHolder.tv =v.findViewById(R.id.petName);
            viewHolder.tv3 =v.findViewById(R.id.systemDate);
            viewHolder.image=v.findViewById(R.id.imageView);

        }
        else
        {
            //直接抓存在類別裡的元件，不必再 findViewById
            //getTag 在資源回收桶取出東西
            viewHolder = (Myadpter.ViewHolder) v.getTag();
        }

        //取出值

        viewHolder.tv.setText(imagelist.get(position).petName.toString());
        viewHolder.tv3.setText(imagelist.get(position).date.toString());
        Glide.with(context).load(imagelist.get(position).uri).into(viewHolder.image);


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
