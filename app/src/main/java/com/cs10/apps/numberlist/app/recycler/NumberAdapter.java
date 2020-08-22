package com.cs10.apps.numberlist.app.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs10.apps.numberlist.databinding.ItemNumberBinding;

import java.util.ArrayList;
import java.util.List;

public class NumberAdapter extends RecyclerView.Adapter<NumberHolder> {
    private List<Integer> integerList;
    private Context context;
    private NumberListener listener;

    public NumberAdapter(Context context, NumberListener listener) {
        this.integerList = new ArrayList<>();
        this.context = context;
        this.listener = listener;
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public void setIntegerList(List<Integer> integerList) {
        this.integerList = integerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NumberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NumberHolder(ItemNumberBinding.inflate(
                LayoutInflater.from(context), parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberHolder holder, int position) {
        holder.binding.number.setText(String.valueOf(integerList.get(position)));
    }

    @Override
    public int getItemCount() {
        return integerList.size();
    }
}
