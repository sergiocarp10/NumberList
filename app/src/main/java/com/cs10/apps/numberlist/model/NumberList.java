package com.cs10.apps.numberlist.model;

import java.util.ArrayList;
import java.util.List;

public class NumberList {
    private int numberGroup;
    private List<Integer> list;

    public NumberList(int numberGroup) {
        this.numberGroup = numberGroup;
        this.setList(new ArrayList<>());
    }

    public int getNumberGroup() {
        return numberGroup;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public void add(Integer aNumber){
        this.getList().add(aNumber);
    }

    public boolean remove(Integer number){
        return this.getList().remove(number);
    }
}
