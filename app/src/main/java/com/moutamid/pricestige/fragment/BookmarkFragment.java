package com.moutamid.pricestige.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fxn.stash.Stash;
import com.moutamid.pricestige.R;
import com.moutamid.pricestige.adapter.BookmarkAdapter;
import com.moutamid.pricestige.constant.Constants;
import com.moutamid.pricestige.databinding.FragmentBookmarkBinding;
import com.moutamid.pricestige.model.BookmarkModel;

import java.util.ArrayList;

public class BookmarkFragment extends Fragment {
    FragmentBookmarkBinding binding;
    public BookmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBookmarkBinding.inflate(getLayoutInflater(), container, false);

        binding.recycler.setHasFixedSize(false);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        ArrayList<BookmarkModel> bookmarkModels = Stash.getArrayList(Constants.bookList, BookmarkModel.class);

        BookmarkAdapter adapter = new BookmarkAdapter(requireContext(), bookmarkModels);
        binding.recycler.setAdapter(adapter);

        return binding.getRoot();
    }
}