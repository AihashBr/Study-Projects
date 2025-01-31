package com.br.cameraapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Adapter  extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<conteudoDb> itemL;

    public Adapter(ArrayList<conteudoDb> itemL) {
        this.itemL = itemL;
    }
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_foto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        conteudoDb item = itemL.get(position);
        holder.iImage.setImageBitmap(item.getImage());
        holder.btnDeletar.setTag(position);
    }

    @Override
    public int getItemCount() {
        return itemL.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iImage;
        FloatingActionButton btnDeletar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iImage = itemView.findViewById(R.id.iImage);
            btnDeletar = itemView.findViewById(R.id.btnDeletar);
        }
    }
}