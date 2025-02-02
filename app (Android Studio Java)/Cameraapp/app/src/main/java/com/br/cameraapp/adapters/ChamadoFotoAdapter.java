package com.br.cameraapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.br.cameraapp.R;
import com.br.cameraapp.models.ChamadoFoto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

public class ChamadoFotoAdapter extends RecyclerView.Adapter<ChamadoFotoAdapter.ViewHolder> {
    private ArrayList<ChamadoFoto> fotos;

    public ChamadoFotoAdapter(ArrayList<ChamadoFoto> fotos) {
        this.fotos = fotos;
    }
    @Override
    public ChamadoFotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_foto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChamadoFotoAdapter.ViewHolder holder, int position) {
        ChamadoFoto item = fotos.get(position);
        File imgFile = new File(item.getImage());

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.iImage.setImageBitmap(myBitmap);
        }

        holder.fabDeletar.setTag(position);
    }

    @Override
    public int getItemCount() {
        return fotos.size();
    }

    private void deletarFoto(View view, CardView carFoto) {
        File file = new File(String.valueOf(fotos.get((int) view.getTag()).getImage()));
        file.delete();
        fotos.remove((int) view.getTag());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView carFoto;
        ImageView iImage;
        FloatingActionButton fabDeletar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carFoto = itemView.findViewById(R.id.carFoto);
            iImage = itemView.findViewById(R.id.iImage);
            fabDeletar = itemView.findViewById(R.id.fabDeletar);

            fabDeletar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deletarFoto(view, carFoto);
                }
            });
        }
    }
}