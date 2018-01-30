package com.lookforpet.pet.data;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/30.
 */

public interface PetDAOControll {

    public boolean add(PetData p);
    public ArrayList<PetData> getList();
    // public PetData getStudent(int id);
    public PetData getStudent(String petName);
    public boolean update(PetData s);
    public boolean delete(String petName);
    //public boolean delete(int id);







}
