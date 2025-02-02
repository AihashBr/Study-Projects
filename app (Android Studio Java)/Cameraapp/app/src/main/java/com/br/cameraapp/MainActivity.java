package com.br.cameraapp;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.br.cameraapp.adapters.ChamadoFotoAdapter;
import com.br.cameraapp.models.ChamadoFoto;
import com.br.cameraapp.utilitarios.Utilitarios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private ArrayList<ChamadoFoto> mFotos = new ArrayList<>();
    private String ultimoCaminhoFoto;
    private Context mContext;
    private ChamadoFotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ChamadoFotoAdapter(mFotos);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabCamera = findViewById(R.id.fabCamera);
        // Define o clique no botão para abrir a câmera
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Verifica se a permissão da câmera foi concedida
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) mContext,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_PERMISSION);
                    return; // Sai do método para aguardar a permissão ser concedida
                }

                try {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

                    // Define o diretório onde as fotos serão salvas
                    File storageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "chamados");
                    if (!storageDir.exists()) {
                        storageDir.mkdirs();
                    }

                    // Cria um arquivo temporário para armazenar a foto capturada
                    File imageFile = File.createTempFile(
                            "Chamado_" + timeStamp + "_",
                            ".jpg",
                            storageDir
                    );

                    ultimoCaminhoFoto = imageFile.getAbsolutePath();

                    // Obtém a URI do arquivo para que possa ser compartilhada com a câmera
                    Uri imageUri = FileProvider.getUriForFile(
                            mContext,
                            "com.br.cameraapp.fileprovider",
                            imageFile
                    );

                    // Cria uma Intent para abrir a câmera
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);

                    // Concede permissões temporárias para a câmera acessar o arquivo
                    cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    cameraIntent.setClipData(ClipData.newRawUri("", imageUri));

                    startActivityForResult(cameraIntent, REQUEST_CAMERA);
                } catch (IOException e) {}
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verifica se o resultado é da captura da câmera e se foi bem-sucedido
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            mFotos.add(new ChamadoFoto(ultimoCaminhoFoto));

            try {
                // Chama o método utilitário para corrigir a rotação da imagem
                Utilitarios.corrigirRotacaoFoto(ultimoCaminhoFoto);
                adapter.notifyDataSetChanged();
            } catch (IOException e) {}
        }
    }
}
