package com.br.cameraapp;

import android.graphics.Bitmap;

public class conteudoDb {
    private Bitmap image;
    public conteudoDb(Bitmap image) {
        this.image = image;
    }
    public Bitmap getImage() {return image;}
}