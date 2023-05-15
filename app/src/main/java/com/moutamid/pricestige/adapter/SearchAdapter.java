package com.moutamid.pricestige.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moutamid.pricestige.R;
import com.moutamid.pricestige.model.ItemModel;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchVH> {
    Context context;
    ArrayList<ItemModel> list;
    public SearchAdapter(Context context, ArrayList<ItemModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchVH(LayoutInflater.from(context).inflate(R.layout.search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchVH holder, int position) {
        ItemModel model = list.get(holder.getAdapterPosition());

        Glide.with(context).load(model.getImage()).into(holder.imageView);
        holder.name.setText(model.getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SearchVH extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        public SearchVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
        }
    }

}
