package com.cs10.apps.numberlist.app.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs10.apps.numberlist.databinding.ItemNumberBinding;

class NumberHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
    protected ItemNumberBinding binding;
    private NumberListener listener;

    public NumberHolder(@NonNull ItemNumberBinding binding, NumberListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;

        this.binding.getRoot().setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        listener.onLongClick(getAdapterPosition());
        return true;
    }
}
