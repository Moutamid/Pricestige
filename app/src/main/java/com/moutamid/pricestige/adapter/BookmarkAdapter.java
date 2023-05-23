package com.moutamid.pricestige.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.moutamid.pricestige.R;
import com.moutamid.pricestige.constant.Constants;
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

        ArrayList<BookmarkModel> bookmarkModels = Stash.getArrayList(Constants.bookList, BookmarkModel.class);

        for (BookmarkModel fvrtModel : bookmarkModels){
            if (fvrtModel.getId().equals(model.getId())){
                holder.bookmark.setImageResource(R.drawable.ic_bookmark_check);
                holder.isBookmarked = true;
            }
        }

        holder.bookmark.setOnClickListener(v -> {
            ArrayList<BookmarkModel> favrtList = Stash.getArrayList(Constants.bookList, BookmarkModel.class);
            if (holder.isBookmarked){
                for (int i = 0; i < favrtList.size(); i++) {
                    if (favrtList.get(i).getId().equals(model.getId())) {
                        Toast.makeText(context, "removed", Toast.LENGTH_SHORT).show();
                        favrtList.remove(i);
                    }
                }
                holder.bookmark.setImageResource(R.drawable.ic_bookmark_border);
                holder.isBookmarked = false;
                Stash.put(Constants.bookList, favrtList);
            } else {
                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
                favrtList.add(model);
                Stash.put(Constants.bookList, favrtList);
                holder.bookmark.setImageResource(R.drawable.ic_bookmark_check);
                holder.isBookmarked = true;
            }
        });

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(model.getLink()));
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BookmarkVH extends RecyclerView.ViewHolder {
        TextView name, price, from;
        ImageView imageView, bookmark;
        boolean isBookmarked;
        public BookmarkVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            from = itemView.findViewById(R.id.from);
            bookmark = itemView.findViewById(R.id.bookmark);
            imageView = itemView.findViewById(R.id.image);
            isBookmarked = false;
        }
    }

}
