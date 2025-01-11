package com.br.sqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter  extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<conteudoDb> itemL;

    public Adapter(ArrayList<conteudoDb> itemL) {
        this.itemL = itemL;
    }
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        conteudoDb item = itemL.get(position);
        holder.textItem.setText(item.getText());
        holder.layoutClick.setTag(item.getId());
    }

    @Override
    public int getItemCount() {
        return itemL.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textItem;
        LinearLayout layoutClick;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textItem = itemView.findViewById(R.id.textItem);
            layoutClick = itemView.findViewById(R.id.layoutClick);
        }
    }
}
