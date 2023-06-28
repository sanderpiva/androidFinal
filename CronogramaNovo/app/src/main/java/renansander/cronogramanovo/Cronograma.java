package renansander.cronogramanovo;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import renansander.cronogramanovo.db.DAOObserver;
import renansander.cronogramanovo.db.MateriaDAO;
import renansander.cronogramanovo.domain.Materias;

public class Cronograma extends AppCompatActivity implements DAOObserver {

    private ListView todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronograma);

        //getSupportActionBar().hide();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Cronograma.this, FormActivity.class);
                startActivity(intent);
            }
        });

        todoList = findViewById(R.id.todo_list);
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Materias materia = (Materias) todoList.getItemAtPosition(position);
                Intent intent = new Intent(Cronograma.this, FormActivity.class);

                intent.putExtra("subject-to-edit", materia);
                startActivity(intent);
            }
        });

        registerForContextMenu(todoList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem menuItemRemover = menu.add("Remover");
        MenuItem menuItemAtivar = menu.add("Ativar/Desativar");

        menuItemRemover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                AdapterView.AdapterContextMenuInfo adapterView =
                        (AdapterView.AdapterContextMenuInfo) menuInfo;

                Materias materia = (Materias) todoList.getItemAtPosition(adapterView.position);
                MateriaDAO dao = new MateriaDAO(Cronograma.this);
                dao.delete(materia);
                loadMaterias();

                Toast.makeText(Cronograma.this,
                        "Materias excluida com sucesso", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        menuItemAtivar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                AdapterView.AdapterContextMenuInfo adapterView =
                        (AdapterView.AdapterContextMenuInfo) menuInfo;

                Materias materia = (Materias) todoList.getItemAtPosition(adapterView.position);
                materia.setActive(false);
                MateriaDAO dao = new MateriaDAO(Cronograma.this);
                dao.update(materia);
                loadMaterias();

                Toast.makeText(Cronograma.this,
                        "Materias realizada com sucesso", Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadMaterias();
    }
    private void loadMaterias(){

        MateriaDAO dao = new MateriaDAO(Cronograma.this);

        dao.loadMaterias();
    }

    @Override
    public void loadSuccess(List<Materias> materia) {
        //DAOObserver.super.loadSuccess(tasks);
        ArrayAdapter<Materias> adapter = new ArrayAdapter<>
                (Cronograma.this, android.R.layout.simple_list_item_1, materia);
        todoList.setAdapter(adapter);
    }
}