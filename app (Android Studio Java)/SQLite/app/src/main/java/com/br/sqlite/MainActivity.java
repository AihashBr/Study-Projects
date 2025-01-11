package com.br.sqlite;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DB databaseAPP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseAPP = new DB(this);
        read();
    }

    public void read() {
        databaseAPP.setItemList();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter(databaseAPP.getItemList());
        recyclerView.setAdapter(adapter);
        if (databaseAPP.getAllItems()) {
            adapter.notifyDataSetChanged();
        }
    }
    public void adicionarOpen(View view) {
        FrameLayout layoutAdicionar = findViewById(R.id.layoutAdicionar);
        if (layoutAdicionar.getVisibility() == View.GONE) {
            layoutAdicionar.setVisibility(View.VISIBLE);
        } else {
            layoutAdicionar.setVisibility(View.GONE);
        }
    }

    public void adicionar(View view) {
        EditText inputNome = findViewById(R.id.inputNome);
        EditText inputQuantidade = findViewById(R.id.inputQuantidade);
        databaseAPP.insertItem(inputNome.getText().toString(), Integer.parseInt(inputQuantidade.getText().toString()));
        read();
        adicionarOpen(null);
    }

    public String idClick;
    public void editarOpen(View view) {
        FrameLayout layoutEditar = findViewById(R.id.layoutEditar);
        if (layoutEditar.getVisibility() == View.GONE) {
            idClick = view.getTag().toString();
            layoutEditar.setVisibility(View.VISIBLE);
        } else {
            layoutEditar.setVisibility(View.GONE);
        }
    }
    public void editar(View view) {
        Log.d("DatabaseHelper", "ID: " + idClick);
        EditText inputNome = findViewById(R.id.inputEditarNome);
        EditText inputQuantidade = findViewById(R.id.inputEditarQuantidade);
        databaseAPP.editItem(Integer.parseInt(idClick), inputNome.getText().toString(), Integer.parseInt(inputQuantidade.getText().toString()));
        read();
        editarOpen(null);
    }

    public void deletar(View view) {
        Log.d("DatabaseHelper", "ID: " + idClick);
        databaseAPP.deleteItem(idClick);
        read();
        editarOpen(null);
    }
}