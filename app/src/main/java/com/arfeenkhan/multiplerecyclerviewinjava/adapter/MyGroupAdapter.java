package com.arfeenkhan.multiplerecyclerviewinjava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arfeenkhan.multiplerecyclerviewinjava.R;
import com.arfeenkhan.multiplerecyclerviewinjava.model.ItemData;
import com.arfeenkhan.multiplerecyclerviewinjava.model.ItemGroup;

import java.util.List;

public class MyGroupAdapter extends RecyclerView.Adapter<MyGroupAdapter.MyGroupViewHolder> {
    private Context context;
    private List<ItemGroup> itemGroupList;

    public MyGroupAdapter(Context context, List<ItemGroup> itemGroupList) {
        this.context = context;
        this.itemGroupList = itemGroupList;
    }

    @NonNull
    @Override
    public MyGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_group, parent, false);
        return new MyGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyGroupViewHolder holder, int position) {
        ItemGroup ig = itemGroupList.get(position);
        holder.itemTitle.setText(ig.getHeaderTitle());

        List<ItemData> itemData = itemGroupList.get(position).getListItem();

        MyItemAdapter itemAdapter = new MyItemAdapter(context, itemData);
        holder.recycler_view_list.setHasFixedSize(true);
        holder.recycler_view_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_view_list.setAdapter(itemAdapter);
        holder.recycler_view_list.setNestedScrollingEnabled(false);

        //Button
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Button More"+holder.itemTitle.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemGroupList.size();
    }

    public class MyGroupViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle;
        Button btnMore;
        RecyclerView recycler_view_list;

        public MyGroupViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.itemTitle);
            btnMore = itemView.findViewById(R.id.btnMore);
            recycler_view_list = itemView.findViewById(R.id.recycler_view_list);
        }
    }
}
