package com.example.suelliton.limi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.suelliton.limi.models.Aplicacao;
import com.example.suelliton.limi.models.Dieta;
import com.example.suelliton.limi.models.Racao;
import com.example.suelliton.limi.models.Tratamento;
import com.example.suelliton.limi.utils.MyDatabaseUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.suelliton.limi.Principal.DIETAS;
import static com.example.suelliton.limi.RacaoActivity.RACOES;
import static com.example.suelliton.limi.Splash.LOGADO;

public class DadosActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference usuarioReference;
    String nomeUsuario;
    String nomeDieta;
    String nomeRacao;
    Button btnSalvarDados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);
        database = MyDatabaseUtil.getDatabase();
        usuarioReference = database.getReference("usuarios");
        Bundle bundle = getIntent().getExtras();
        nomeUsuario = bundle.getString("nomeUsuario");
        nomeDieta = bundle.getString("nomeDieta");
        nomeRacao = bundle.getString("nomeRacao");


        btnSalvarDados = (Button) findViewById(R.id.btn_salvar_dados);
        btnSalvarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAplicacoes();
                salvarTratamentos();
            }
        });


    }
    public void salvarTratamentos(){
        for (Racao r: RACOES) {
            if(r.getNome().equals(nomeRacao)){
                List<Tratamento> tratamentos;
                if(r.getTratamentos() != null){
                    tratamentos = r.getTratamentos();
                }else{
                    tratamentos = new ArrayList<>();
                }
                Tratamento tr = new Tratamento(1,"gato1");
                tratamentos.add(tr);
                r.setTratamentos(tratamentos);

                for (Dieta d : DIETAS) {
                    if(d.getNome().equals(nomeDieta)){
                        d.setRacoes(RACOES);
                        usuarioReference.child(nomeUsuario).child("dietas").setValue(DIETAS);
                        finish();
                    }
                }
            }
        }
    }

    public void salvarAplicacoes(){
        for (Racao r: RACOES) {
            if(r.getNome().equals(nomeRacao)){
                List<Aplicacao> aplicacoes;
                if(r.getAplicacoes() != null){
                    aplicacoes = r.getAplicacoes();
                }else{
                    aplicacoes = new ArrayList<>();
                }
                Aplicacao ap = new Aplicacao(1,1,2018,10,10,20,50);
                aplicacoes.add(ap);
                r.setAplicacoes(aplicacoes);

                for (Dieta d : DIETAS) {
                    if(d.getNome().equals(nomeDieta)){
                        d.setRacoes(RACOES);
                        usuarioReference.child(nomeUsuario).child("dietas").setValue(DIETAS);
                        finish();
                    }
                }
            }
        }

    }


}
