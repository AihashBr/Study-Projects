package com.br.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {
    private ArrayList<conteudoDb> itemList = new ArrayList<>();
    private static final String DATABASE_nome = "SimpleDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DB(Context context) {
        super(context, DATABASE_nome, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS databaseAPP (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, quantidade INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertItem(String nome, int quantidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("quantidade", quantidade);
        db.insert("databaseAPP", null, values);
        Log.d("DatabaseHelper", "Item inserido: " + nome);
    }

    public boolean getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM databaseAPP", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            int quantidade = cursor.getInt(2);
            itemList.add(new conteudoDb(id, nome, quantidade));
            Log.d("DatabaseHelper", "ID: " + id + ", Nome: " + nome + ", Quantidade: " + quantidade);
        }
        cursor.close();
        return true;
    }

    public ArrayList<conteudoDb> getItemList() {return itemList;}
    public void setItemList() {itemList.clear();}

    public void editItem(int id, String nome, int quantidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE databaseAPP SET nome = ?, quantidade = ? WHERE id = ?;", new String[]{nome, String.valueOf(quantidade), String.valueOf(id)});
    }

    public void deleteItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("databaseAPP", "id = ?", new String[]{id});
        Log.d("DatabaseHelper", "Item deletado com ID: " + id);
    }
}
