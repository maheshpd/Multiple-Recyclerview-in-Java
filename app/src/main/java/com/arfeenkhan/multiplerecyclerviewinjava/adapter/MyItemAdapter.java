package com.arfeenkhan.multiplerecyclerviewinjava.adapter;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arfeenkhan.multiplerecyclerviewinjava.Interface.IItemClickListener;
import com.arfeenkhan.multiplerecyclerviewinjava.R;
import com.arfeenkhan.multiplerecyclerviewinjava.model.ItemData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyItemAdapter extends RecyclerView.Adapter<MyItemAdapter.MyViewHolder> {

    private Context context;
    private List<ItemData> itemList;

    public MyItemAdapter(Context context, List<ItemData> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemData im = itemList.get(position);
        holder.txt_title.setText(im.getName());
        Picasso.get().load(im.getImage()).into(holder.img_item);

        holder.setClick(new IItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(context, ""+itemList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_title;
        ImageView img_item;

        IItemClickListener itemClickListener;

        void setClick(IItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.tvTitle);
            img_item = itemView.findViewById(R.id.itemImage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClickListener(view, getAdapterPosition());
        }
    }
}
