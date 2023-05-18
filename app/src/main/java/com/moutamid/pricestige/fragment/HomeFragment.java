package com.moutamid.pricestige.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.moutamid.pricestige.R;
import com.moutamid.pricestige.ResultActivity;
import com.moutamid.pricestige.constant.Constants;
import com.moutamid.pricestige.databinding.FragmentHomeBinding;
import com.moutamid.pricestige.model.ItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    ArrayList<ItemModel> list;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);

        list = new ArrayList<>();

        Constants.initDialog(requireContext());

        binding.search.setOnClickListener(v -> {
            if (binding.etSearch.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(requireContext(), "Search Item is empty", Toast.LENGTH_SHORT).show();
            } else {
                Constants.showDialog();
                getData(binding.etSearch.getEditText().getText().toString());
            }
        });

        return binding.getRoot();
    }

    private void getData(String item) {
        String finalName = item;
        item = Constants.ebayLink(item);
        String finalItem = item;

        Log.d("TAGDATA", "finalItem: " + finalItem);

        new Thread(() -> {
            URL google = null;
            try {
                google = new URL(finalItem);
            } catch (final MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(google != null ? google.openStream() : null));
            } catch (final IOException e) {
                Log.d("TAG", "compress: ERROR: " + e);
                e.printStackTrace();
            }
            String input = null;
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if ((input = in != null ? in.readLine() : null) == null) break;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                stringBuffer.append(input);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String htmlData = stringBuffer.toString();

            Log.d("TAGDATA", "data: " + htmlData);

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    try {
                        JSONObject object = new JSONObject(htmlData);
                        JSONArray array = object.getJSONArray("search_results");

                        for (int i = 0; i < array.length(); i++) {
                            try {
                                ItemModel model = new ItemModel(
                                        array.getJSONObject(i).getInt("position"),
                                        array.getJSONObject(i).getString("title"),
                                        array.getJSONObject(i).getString("epid"),
                                        array.getJSONObject(i).getString("link"),
                                        array.getJSONObject(i).getString("image"),
                                        array.getJSONObject(i).getString("condition"),
                                        array.getJSONObject(i).getBoolean("is_auction"),
                                        array.getJSONObject(i).getBoolean("buy_it_now"),
                                        array.getJSONObject(i).getBoolean("free_returns"),
                                        array.getJSONObject(i).getBoolean("sponsored"),
                                        array.getJSONObject(i).getJSONObject("price").getString("raw"),
                                        finalName
                                );
                                list.add(model);
                            }  catch (JSONException error) {
                                Log.d("TAGDATA", "Error : " + error.getMessage());
                            }
                        }
                        Constants.dismissDialog();
                        Stash.put(Constants.Result, list);
                        startActivity(new Intent(requireContext(), ResultActivity.class));
                    } catch (JSONException error) {
                        Constants.dismissDialog();
                        Log.d("TAGDATA", "Error : " + error.getMessage());
                        Toast.makeText(requireActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }

}