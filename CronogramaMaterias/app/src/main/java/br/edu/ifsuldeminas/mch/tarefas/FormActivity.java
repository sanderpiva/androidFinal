package br.edu.ifsuldeminas.mch.tarefas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsuldeminas.mch.tarefas.db.MateriaDAO;
import br.edu.ifsuldeminas.mch.tarefas.domain.Materia;

public class FormActivity extends AppCompatActivity {

    private TextInputEditText description;
    private TextInputEditText room;
    private TextInputEditText week_day;
    private TextInputEditText hour;
    private Materia materia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        description = findViewById(R.id.subject_description);
        room = findViewById(R.id.room);
        week_day = findViewById(R.id.week_day);
        hour = findViewById(R.id.hour);

        Intent intent = getIntent();
        materia = (Materia) intent.getSerializableExtra("task-to-edit");

        if(materia != null){
            description.setText(materia.getDescription());
            room.setText(materia.getRoom());
            week_day.setText(materia.getWeek_day());
            hour.setText(materia.getHour());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //A linha abaixo infla o arquivo de menu menu_form.xml no objeto
        //menu passado como parâmetro.
        //O arquivo de menu define os itens e ações que estarão disponíveis no menu de opções da atividade.
        getMenuInflater().inflate(R.menu.menu_form, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String desc = description.getText().toString();
        String roomText = room.getText().toString();
        String week_dayText = week_day.getText().toString();
        String hourText = hour.getText().toString();

        if(desc.equals("")||(roomText.equals("")||week_dayText.equals("")||
                hourText.equals(""))){

            Toast.makeText(FormActivity.this, "Preencha todos os dados"
            , Toast.LENGTH_SHORT).show();

            return super.onOptionsItemSelected(item);

        }else{

            MateriaDAO dao = new MateriaDAO(FormActivity.this);

            if(materia == null){
                materia = new Materia(null, desc, roomText, week_dayText, hourText,true);
                dao.save(materia);
            }else{
                materia.setDescription(desc);
                materia.setRoom(roomText);
                materia.setWeek_day(week_dayText);
                materia.setHour(hourText);
                dao.update(materia);
            }

            finish();
        }

        return super.onOptionsItemSelected(item);

    }
}