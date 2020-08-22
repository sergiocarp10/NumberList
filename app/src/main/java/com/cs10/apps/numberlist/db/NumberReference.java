package com.cs10.apps.numberlist.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cs10.apps.numberlist.model.NumberList;
import com.cs10.apps.numberlist.other.Constants;

public class NumberReference {
    private SharedPreferences sharedPreferences;

    public NumberReference(@NonNull Context context){
        sharedPreferences = context.getSharedPreferences("numbers", Context.MODE_PRIVATE);
    }

    public void save(@NonNull NumberList numberList){
        StringBuilder stringBuilder = new StringBuilder();

        for (int n : numberList.getList())
            stringBuilder.append(n).append('-');

        String key = String.valueOf(numberList.getNumberGroup());
        sharedPreferences.edit().putString(key, stringBuilder.toString()).apply();
    }

    public NumberList get(int numberGroup){
        String key = String.valueOf(numberGroup);
        String value = sharedPreferences.getString(key, null);
        NumberList numberList = new NumberList(numberGroup);

        if (value != null) {
            String[] numbers = value.split("-");
            Log.i(Constants.TAG, numbers.length + " numbers retrieved");
            for (String num : numbers) {
                try {
                    numberList.add(Integer.parseInt(num));
                } catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }

        return numberList;
    }
}
