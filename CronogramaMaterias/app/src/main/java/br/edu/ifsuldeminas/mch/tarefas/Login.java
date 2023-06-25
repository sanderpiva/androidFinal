package br.edu.ifsuldeminas.mch.tarefas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private EditText nome;
    private EditText idade;
    private ProgressBar pb;
    private static final String PREFS_KEY = "dados";
    private static final String NOME = "nome";
    private static final String IDADE = "idade";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nome = findViewById(R.id.editTextNome);
        idade = findViewById(R.id.editTextIdade);
        Button logar = findViewById(R.id.btnLogar);
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nomeCapturado = nome.getText().toString();
                String idadeCapturada = idade.getText().toString();

                if (!nomeCapturado.isEmpty() && !idadeCapturada.isEmpty()) {

                    int idadeChecada = Integer.parseInt(idadeCapturada);

                    if(idadeChecada>13){

                        pb.setVisibility(View.VISIBLE);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                pb.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(getBaseContext(), Cronograma.class);
                                intent.putExtra("NOME", nomeCapturado);
                                intent.putExtra("IDADE", idadeCapturada);
                                startActivity(intent);
                            }
                        }, 4000);

                    }else{
                        Toast.makeText(view.getContext(), "Idade: maior 14 anos", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(view.getContext(), "Preencha todos os dados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = preferences.edit();
        String nomeCapturado = nome.getText().toString();
        String idadeCapturada = idade.getText().toString();

        if(!nomeCapturado.equals("")){
            editor.putString(NOME, nomeCapturado);
            editor.apply();
        }

        if(!idadeCapturada.equals("")){
            editor.putString(IDADE, idadeCapturada);
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        preferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);

        if(preferences.contains(NOME)){

            String nomeCapturado = preferences.getString(NOME,"");
            nome.setText(nomeCapturado);
        }

        if(preferences.contains(IDADE)){

            String idadeCapturada = preferences.getString(IDADE,"");
            idade.setText(idadeCapturada);
        }
    }
}
