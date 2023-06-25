package br.edu.ifsuldeminas.mch.tarefas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.mch.tarefas.domain.Materia;

public class MateriaDAO extends DAO {

    public MateriaDAO(Context context){

        super(context);
    }

    public boolean save(Materia materia){

        SQLiteDatabase db = openToWrite();
        ContentValues values = new ContentValues();
        values.put("description", materia.getDescription());
        values.put("room", materia.getRoom());
        values.put("week_day", materia.getWeek_day());
        values.put("hour", materia.getHour());

        String active = materia.getActive()? "1" : "0";

        values.put("active", active);

        db.insert("materias", null, values);
        db.close();
        return true;
    }

    public void update(Materia materia){

        SQLiteDatabase db = openToWrite();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", materia.getDescription());
        contentValues.put("room", materia.getRoom());
        contentValues.put("week_day", materia.getWeek_day());
        contentValues.put("hour", materia.getHour());
        contentValues.put("active", materia.getActive()? "1":"0");

        String[] params = {materia.getId().toString()};
        db.update("materias", contentValues, "id = ?", params);
        db.close();

    }

    public void delete(Materia materia){
        SQLiteDatabase db = openToWrite();
        String[] params = {materia.getId().toString()};
        db.delete("materias", "id = ?", params);
        db.close();

    }

    public List<Materia> listAll(){

        SQLiteDatabase db = openToRead();
        List<Materia> s = new ArrayList<>();

        String sql = "SELECT * FROM materias;";

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String desc = cursor.getString((cursor.getColumnIndexOrThrow("description")));
            String room = cursor.getString((cursor.getColumnIndexOrThrow("room")));
            String week_day = cursor.getString((cursor.getColumnIndexOrThrow("week_day")));
            String hour = cursor.getString((cursor.getColumnIndexOrThrow("hour")));
            String activeStr = cursor.getString(cursor.getColumnIndexOrThrow("active"));
            Boolean active = activeStr.equals("1")? true:false;

            Materia materia = new Materia(id, desc, room, week_day, hour, active);
            s.add(materia);
        }
        cursor.moveToNext();
        db.close();
        return s;
    }
}
