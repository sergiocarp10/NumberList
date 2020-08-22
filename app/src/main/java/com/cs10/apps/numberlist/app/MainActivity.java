package com.cs10.apps.numberlist.app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cs10.apps.numberlist.R;
import com.cs10.apps.numberlist.app.recycler.NumberAdapter;
import com.cs10.apps.numberlist.app.recycler.NumberListener;
import com.cs10.apps.numberlist.databinding.ActivityMainBinding;
import com.cs10.apps.numberlist.databinding.InputNumberBinding;
import com.cs10.apps.numberlist.db.NumberReference;
import com.cs10.apps.numberlist.model.NumberList;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements
        NumberPicker.OnValueChangeListener, NumberListener, View.OnClickListener {
    private ActivityMainBinding binding;
    private NumberAdapter adapter;
    private NumberReference numberReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        numberReference = new NumberReference(this);
        adapter = new NumberAdapter(this, this);
        binding.recycler.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setAdapter(adapter);
        binding.numberPicker.setMinValue(0);
        binding.numberPicker.setMaxValue(99);

        binding.numberPicker.setValue(new Random().nextInt() % 100);
        binding.numberPicker.setOnValueChangedListener(this);
        binding.fab.setOnClickListener(this);

        // force to show start group
        NumberList numberList = numberReference.get(binding.numberPicker.getValue());
        adapter.setIntegerList(numberList.getList());
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        NumberList numberList = numberReference.get(newVal);
        adapter.setIntegerList(numberList.getList());
    }

    @Override
    public void onLongClick(int position) {
        int number = adapter.getIntegerList().get(position);
        int numberGroup = binding.numberPicker.getValue();

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_from, numberGroup))
                .setMessage(getString(R.string.ask_for_delete, number))
                .setPositiveButton(getString(android.R.string.yes), (dialog, which) -> {
                    NumberList numberList = numberReference.get(numberGroup);
                    if (numberList.remove(number)){
                        adapter.setIntegerList(numberList.getList());
                        numberReference.save(numberList);
                        showDeleteSuccessful();
                    } else showDeleteFailure();
                }).setNeutralButton(getString(R.string.cancel), null).show();
    }

    @Override
    public void onClick(View v) {
        int numberGroup = binding.numberPicker.getValue();
        InputNumberBinding inputNumberBinding = InputNumberBinding.inflate(getLayoutInflater());
        inputNumberBinding.numberPicker.setMinValue(0);
        inputNumberBinding.numberPicker.setMaxValue(99);
        inputNumberBinding.numberPicker.setValue(numberGroup);

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.add_to, numberGroup))
                .setView(inputNumberBinding.getRoot())
                .setPositiveButton(getString(R.string.save), (dialog, which) -> {
                    NumberList numberList = numberReference.get(numberGroup);
                    numberList.add(inputNumberBinding.numberPicker.getValue());
                    adapter.setIntegerList(numberList.getList());
                    numberReference.save(numberList);
                    showAddSuccessful();
                }).setNeutralButton(getString(R.string.cancel), null).show();
    }

    private void showAddSuccessful(){
        Snackbar.make(binding.getRoot(), getString(R.string.add_successful),
                Snackbar.LENGTH_LONG).show();
    }

    private void showDeleteSuccessful(){
        Snackbar.make(binding.getRoot(), getString(R.string.delete_successful),
                Snackbar.LENGTH_LONG).show();
    }

    private void showDeleteFailure(){
        Snackbar.make(binding.getRoot(),getString(R.string.delete_failure),
                Snackbar.LENGTH_LONG).show();
    }
}