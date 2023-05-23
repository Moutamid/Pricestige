package com.moutamid.pricestige;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.fxn.stash.Stash;
import com.moutamid.pricestige.adapter.BookmarkAdapter;
import com.moutamid.pricestige.adapter.SearchAdapter;
import com.moutamid.pricestige.constant.Constants;
import com.moutamid.pricestige.databinding.ActivitySearchBinding;
import com.moutamid.pricestige.model.BookmarkModel;
import com.moutamid.pricestige.model.ItemModel;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.recycler.setHasFixedSize(false);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<BookmarkModel> list = new ArrayList<>();

        ItemModel model = (ItemModel) Stash.getObject(Constants.model, ItemModel.class);
        String name = Stash.getString("na");

        list.add(new BookmarkModel(model.getEpid(), name, model.getPrice(), "Ebay", model.getImage(), model.getLink(), false));

        BookmarkAdapter adapter = new BookmarkAdapter(this, list);
        binding.recycler.setAdapter(adapter);

    }
}