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
import com.moutamid.pricestige.model.BookmarkModel;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkVH> {
    Context context;
    ArrayList<BookmarkModel> list;

    public BookmarkAdapter(Context context, ArrayList<BookmarkModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BookmarkVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookmarkVH(LayoutInflater.from(context).inflate(R.layout.bookmark_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkVH holder, int position) {
        BookmarkModel model = list.get(holder.getAdapterPosition());

        holder.price.setText("Price : " + model.getPrice());
        holder.from.setText("From : " + model.getFrom());
        holder.name.setText("Name : " + model.getTitle().toUpperCase());

        Glide.with(context).load(model.getImage()).into(holder.imageView);

        if (model.isBook()){
            holder.bookmark.setImageResource(R.drawable.ic_bookmark_check);
        } else {
            holder.bookmark.setImageResource(R.drawable.ic_bookmark_border);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BookmarkVH extends RecyclerView.ViewHolder {
        TextView name, price, from;
        ImageView imageView, bookmark;
        public BookmarkVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            from = itemView.findViewById(R.id.from);
            bookmark = itemView.findViewById(R.id.bookmark);
            imageView = itemView.findViewById(R.id.image);
        }
    }

}
