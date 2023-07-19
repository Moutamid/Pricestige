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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fxn.stash.Stash;
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
import java.util.HashMap;
import java.util.Map;

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
        String walmart = Constants.walmartLink(finalName);

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
                Log.d("TAGDATA", "compress: ERROR: " + e);
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
                                        i + 1,
                                        array.getJSONObject(i).getString("title"),
                                        array.getJSONObject(i).getString("epid"),
                                        array.getJSONObject(i).getString("link"),
                                        array.getJSONObject(i).getString("image"),
                                        array.getJSONObject(i).getString("condition"),
                                        array.getJSONObject(i).getBoolean("is_auction"),
                                        array.getJSONObject(i).getBoolean("buy_it_now"),
                                        array.getJSONObject(i).getBoolean("free_returns"),
                                        false,
                                        // array.getJSONObject(i).getBoolean("sponsored"),
                                        array.getJSONObject(i).getJSONObject("price").getString("raw"),
                                        finalName, "ebay"
                                );
                                list.add(model);
                            } catch (JSONException error) {
                                Log.d("TAGDATA", "Error : " + error.getMessage());
                            }
                        }
                        // Constants.dismissDialog();
                        Stash.put(Constants.Result, list);
                        //  startActivity(new Intent(requireContext(), ResultActivity.class));
                        getWalmart(walmart, finalName);
                    } catch (JSONException error) {
                        getWalmart(walmart, finalName);
                    }
                });
            }
        }).start();
    }

    private void getWalmart(String walmart, String finalName) {
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-RapidAPI-Key", Constants.X_RapidAPI_Key);
        headers.put("X-RapidAPI-Host", Constants.X_RapidAPI_Host);

        Log.d("TAGDATA", walmart);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, walmart, null,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        JSONObject props = response.getJSONObject("item").getJSONObject("props");
                        JSONObject searchResult = props.getJSONObject("pageProps").getJSONObject("initialData").getJSONObject("searchResult");
                        JSONArray itemStacks = searchResult.getJSONArray("itemStacks");
                        JSONArray items = itemStacks.getJSONObject(0).getJSONArray("items");
                        ArrayList<ItemModel> arrayList = Stash.getArrayList(Constants.Result, ItemModel.class);
                        for (int i = 0; i < items.length(); i++) {
                            try {
                                ItemModel model = new ItemModel(
                                        arrayList.size() + 1,
                                        items.getJSONObject(i).getString("name"),
                                        items.getJSONObject(i).getString("usItemId"),
                                        Constants.walmartProductLink(items.getJSONObject(i).getString("canonicalUrl")),
                                        items.getJSONObject(i).getString("image"), "Brand New",
                                        false, false, false, false,
                                        items.getJSONObject(i).getJSONObject("priceInfo").getString("itemPrice"),
                                        finalName, "Walmart"
                                );
                                arrayList.add(model);
                            } catch (JSONException error) {
                                Log.d("TAGDATA", "Error : " + error.getMessage());
                            }
                        }
                        Constants.dismissDialog();
                        Stash.put(Constants.Result, arrayList);
                        startActivity(new Intent(requireContext(), ResultActivity.class));
                    } catch (JSONException e) {
                        // ...
                        e.printStackTrace();
                    }
                },
                (Response.ErrorListener) error -> {
                    Constants.dismissDialog();
//                    Stash.put(Constants.Result, list);
//                    Toast.makeText(requireActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(requireContext(), ResultActivity.class));
                    Log.d("TAGDATA", " Catch Error : " + error.getMessage());
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
        queue.add(request);
    }

}