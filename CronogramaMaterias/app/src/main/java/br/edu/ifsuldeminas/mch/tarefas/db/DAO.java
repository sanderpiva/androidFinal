package br.edu.ifsuldeminas.mch.tarefas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

abstract class DAO {

    private DBHandler dbHandler = null;

    DAO(Context context){

        if(dbHandler==null)
            dbHandler = new DBHandler(context);
    }

    SQLiteDatabase openToWrite(){

        return dbHandler.getWritableDatabase();
    }

    SQLiteDatabase openToRead(){

        return dbHandler.getReadableDatabase();
    }
}
