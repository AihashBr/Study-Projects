package com.br.roomdb;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapeter  extends RecyclerView.Adapter<Adapeter.ViewHolder> {
    private final List<DatabaseAppEntity> itemList;
    public Adapeter(List<DatabaseAppEntity> itemList) {
        this.itemList = itemList;
    }
    @Override
    public Adapeter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapeter.ViewHolder holder, int position) {
        DatabaseAppEntity item = itemList.get(position);
        holder.textNome.setText("Nome: "+item.nome+" | Quantidade: "+item.quantidade);
        holder.layoutClick.setTag(item.id);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNome;
        LinearLayout layoutClick;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textNome);
            layoutClick = itemView.findViewById(R.id.layoutClick);
        }
    }
}
