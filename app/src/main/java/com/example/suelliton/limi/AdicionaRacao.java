package com.example.suelliton.limi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.suelliton.limi.models.Dieta;
import com.example.suelliton.limi.models.Racao;
import com.example.suelliton.limi.utils.MyDatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.suelliton.limi.Principal.DIETAS;
import static com.example.suelliton.limi.Splash.LOGADO;

public class AdicionaRacao extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference usuarioReference;
    EditText ed_nome;
    Button btnSalvarRacao ;
    String nomeDieta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_racao);
        database = MyDatabaseUtil.getDatabase();
        usuarioReference = database.getReference("usuarios");
        Bundle bundle = getIntent().getExtras();
        nomeDieta = bundle.getString("nomeDieta");

        ed_nome = (EditText) findViewById(R.id.nome);
        btnSalvarRacao = (Button) findViewById(R.id.btn_salvar_racao);
        btnSalvarRacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Racao racao = new Racao(ed_nome.getText().toString());

                for (Dieta d: DIETAS ) {
                    if(d.getNome().equals(nomeDieta)){
                        List<Racao> racoes;
                        if(d.getRacoes() != null) {
                            racoes = d.getRacoes();
                        }else{
                            racoes =  new ArrayList<>();
                        }
                        racoes.add(racao);
                        d.setRacoes(racoes);
                        usuarioReference.child(LOGADO).child("dietas").setValue(DIETAS);
                        finish();
                    }
                }
            }
        });

    }
}
