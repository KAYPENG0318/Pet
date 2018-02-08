package com.lookforpet.pet.data;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/30.
 */

public class PetDAO  implements  PetDAOControll{

    public ArrayList<PetData> mylist;

    public PetDAO()
    {
        mylist = new ArrayList<>();
    }

    public boolean add(PetData p)
    {
        mylist.add(p);
        Log.d("petDAO mylist~~~~",""+mylist.size());
        return true;
    }

    //傳回 LIST 長度
    public ArrayList<PetData> getList()
    {
        return mylist;
    }


    //查詢
//    public PetData getStudent(int id)
//    {
//
//        for(PetData s: mylist)
//        {
//            if(s.id==id)
//            {
//                return s;
//            }
//        }
//        return null;
//    }

    //查詢
    public PetData getStudent(String uri)
    {
        for(PetData s: mylist)
        {
            if(s.uri.equals(uri))
            {
                //回傳 PetData
                return s;
            }
        }
        return null;
    }



//    //查詢
//    public PetData getStudent(String petName)
//    {
//        for(PetData s: mylist)
//        {
//            if(s.petName.equals(petName))
//            {
//                //回傳 PetData
//                return s;
//            }
//        }
//        return null;
//    }

    @Override
    public boolean update(PetData s) {
        return false;
    }

    @Override
    public boolean delete(String petName) {
        return false;
    }

//    @Override
//    public boolean delete(int id) {
//        return false;
//    }



}
