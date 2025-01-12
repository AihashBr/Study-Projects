package com.br.roomdb;

import android.content.Context;
import android.util.Log;

import java.util.List;

public class DatabaseHelper {
    private static final String TAG = "DatabaseHelper";
    private final DatabaseAppDao dao;

    public DatabaseHelper(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        dao = db.databaseAppDao();
    }

    public void insert(String nome, int quantidade) {
        new Thread(() -> {
            dao.insert(new DatabaseAppEntity(nome, quantidade));
            Log.d(TAG, "Inserido: Nome = " + nome + ", Quantidade = " + quantidade);
        }).start();
    }

    public void getAll(DatabaseCallback callback) {
        new Thread(() -> {
            List<DatabaseAppEntity> items = dao.getAll();
            Log.d(TAG, "Recuperados: " + items.size() + " itens");
            for (DatabaseAppEntity item : items) {
                Log.d(TAG, "ID: " + item.id + ", Nome: " + item.nome + ", Quantidade: " + item.quantidade);
            }
            callback.onDataLoaded(items);
        }).start();
    }

    public void deleteById(int id) {
        new Thread(() -> {
            dao.deleteById(id);
            Log.d(TAG, "Deletado item com ID = " + id);
        }).start();
    }

    public void update(int id, String nome, int quantidade) {
        new Thread(() -> {
            dao.update(id, nome, quantidade);
            Log.d(TAG, "Atualizado: ID = " + id + ", Nome = " + nome + ", Quantidade = " + quantidade);
        }).start();
    }

    // Callback para dados carregados
    public interface DatabaseCallback {
        void onDataLoaded(List<DatabaseAppEntity> items);
    }
}
