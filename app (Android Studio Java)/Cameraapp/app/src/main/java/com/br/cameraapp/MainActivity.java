package com.br.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_CAMERA = 1;

    private ArrayList<conteudoDb> mFotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mFotos = new ArrayList<>();
    }

    private void atualizar () {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Adapter adapter = new Adapter(mFotos);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void btnCamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    public void btnDeletar(View view) {
        int tag = (int) view.getTag();
        mFotos.remove(tag);
        atualizar();
    }

    public void btnSalvar(View view) {
        if (mFotos.isEmpty()) {
            Toast.makeText(this, "Nenhuma foto para salvar!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < mFotos.size(); i++) {
            Bitmap bitmap = mFotos.get(i).getImage();
            String filename = "foto_" + System.currentTimeMillis() + ".png";

            try {
                FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(this, "Imagens salvas com sucesso!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            mFotos.add(new conteudoDb(image));
            atualizar();
        }
    }
}