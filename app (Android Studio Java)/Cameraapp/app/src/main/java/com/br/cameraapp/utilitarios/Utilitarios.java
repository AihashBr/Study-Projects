package com.br.cameraapp.utilitarios;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class Utilitarios {
    public static void corrigirRotacaoFoto(String ultimoCaminhoFoto) throws IOException {
        File file = new File(ultimoCaminhoFoto);

        // Carrega o bitmap original em uma escala menor para evitar alto consumo de memória
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2; // Reduz a escala para evitar consumo excessivo de memória
        Bitmap bitmap = BitmapFactory.decodeFile(ultimoCaminhoFoto, options);

        // Obtém a rotação correta a partir dos metadados EXIF
        ExifInterface exif = new ExifInterface(ultimoCaminhoFoto);
        int orientacao = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int angulo = 0;
        switch (orientacao) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                angulo = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                angulo = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                angulo = 270;
                break;
        }

        // Se for necessário, rotaciona a imagem
        if (angulo != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(angulo);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        // Redimensiona a imagem para 1280x720 mantendo a proporção correta
        int largura = bitmap.getWidth();
        int altura = bitmap.getHeight();
        int novaLargura = 1280;
        int novaAltura = 720;

        if (largura > altura) { // Foto horizontal
            novaLargura = 1280;
            novaAltura = (altura * novaLargura) / largura;
        } else { // Foto vertical
            novaAltura = 1280;
            novaLargura = (largura * novaAltura) / altura;
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, novaLargura, novaAltura, true);

        // Sobrescreve o arquivo original com a imagem corrigida e redimensionada
        FileOutputStream out = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // Qualidade reduzida para 75%
        out.flush();
        out.close();
    }
}
