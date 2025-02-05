package com.br.armazenamentoexternodata;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 100;
    private EditText textTexto;
    private EditText textTitulo;
    private Button buttonSalvar;
    private Button buttonPegar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mContext = this;

        inicializaCampos();

        // Verifica permissões no Android 10 ou inferior
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            verificaPermissoes();
        }

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = textTitulo.getText().toString().trim();
                String conteudo = textTexto.getText().toString().trim();

                if (titulo.isEmpty()) {
                    Toast.makeText(mContext, "Digite um título para o arquivo", Toast.LENGTH_SHORT).show();
                    return;
                }

                File pastaApp = getExternalFilesDir(null);
                if (pastaApp != null) {
                    File arquivo = new File(pastaApp, titulo + ".txt");

                    try (FileOutputStream fos = new FileOutputStream(arquivo)) {
                        fos.write(conteudo.getBytes());
                        Toast.makeText(mContext, "Arquivo salvo em: " + arquivo.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "Erro ao salvar arquivo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonPegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = textTitulo.getText().toString().trim();

                if (titulo.isEmpty()) {
                    Toast.makeText(mContext, "Digite o título do arquivo", Toast.LENGTH_SHORT).show();
                    return;
                }

                File pastaApp = getExternalFilesDir(null);
                if (pastaApp != null) {
                    File arquivo = new File(pastaApp, titulo + ".txt");

                    if (!arquivo.exists()) {
                        Toast.makeText(mContext, "Arquivo não encontrado", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try (FileInputStream fis = new FileInputStream(arquivo)) {
                        int tamanho = (int) arquivo.length();
                        byte[] buffer = new byte[tamanho];
                        fis.read(buffer);

                        textTexto.setText(new String(buffer));
                        Toast.makeText(mContext, "Arquivo carregado com sucesso!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "Erro ao ler arquivo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void inicializaCampos() {
        textTexto = findViewById(R.id.textTexto);
        textTitulo = findViewById(R.id.textTitulo);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonPegar = findViewById(R.id.buttonPegar);
    }

    private void verificaPermissoes() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão concedida!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissão negada!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
