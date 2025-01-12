package com.br.roomdb;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        read();
    }

    private void read() {
        
    }

    public void adicionarOpen(View view) {
        FrameLayout telaAdicionar = findViewById(R.id.telaAdicionar);
        if (telaAdicionar.getVisibility() == View.GONE) {
            telaAdicionar.setVisibility(View.VISIBLE);
        } else {
            telaAdicionar.setVisibility(View.GONE);
        }
    }
    public void adicionar(View view) {
        EditText nome = findViewById(R.id.inputAdicionarNome);
        EditText quantidade = findViewById(R.id.inputAdicionarQuantidade);
        dbHelper.insert(nome.getText().toString(), Integer.parseInt(quantidade.getText().toString()));
        adicionarOpen(null);
    }

    public void editarOpen(View view) {
    }
}