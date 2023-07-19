package com.moutamid.pricestige;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Instrumentation;
import android.os.Bundle;

import com.fxn.stash.Stash;
import com.moutamid.pricestige.adapter.SearchAdapter;
import com.moutamid.pricestige.constant.Constants;
import com.moutamid.pricestige.databinding.ActivityResultBinding;
import com.moutamid.pricestige.model.ItemModel;

import java.util.ArrayList;
import java.util.Collections;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycler.setHasFixedSize(false);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<ItemModel> list = Stash.getArrayList(Constants.Result, ItemModel.class);
        Collections.shuffle(list);
        SearchAdapter adapter = new SearchAdapter(this, list);
        binding.recycler.setAdapter(adapter);

    }
}