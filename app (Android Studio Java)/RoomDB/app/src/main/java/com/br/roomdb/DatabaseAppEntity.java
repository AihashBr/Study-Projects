package com.br.roomdb;

import android.content.Context;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

@Entity(tableName = "databaseAPP")
public class DatabaseAppEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name = "quantidade")
    public int quantidade;

    public DatabaseAppEntity(String nome, int quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }
}

@Dao
interface DatabaseAppDao {
    @Insert
    void insert(DatabaseAppEntity entity);

    @Query("SELECT * FROM databaseAPP")
    List<DatabaseAppEntity> getAll();

    @Query("DELETE FROM databaseAPP WHERE id = :id")
    void deleteById(int id);

    @Query("UPDATE databaseAPP SET nome = :nome, quantidade = :quantidade WHERE id = :id")
    void update(int id, String nome, int quantidade);
}

@Database(entities = {DatabaseAppEntity.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract DatabaseAppDao databaseAppDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "SimpleDatabase.db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}

