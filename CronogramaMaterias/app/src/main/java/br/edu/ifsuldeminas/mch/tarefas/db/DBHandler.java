package br.edu.ifsuldeminas.mch.tarefas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "materias.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS materias" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "description TEXT," +
                    "room TEXT," +
                    "week_day TEXT," +
                    "hour TEXT," +
                    "active VARCHAR(1))";


    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //criar a base dados

        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //aqui nao sera tratado
    }
}
