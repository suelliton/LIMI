package com.example.suelliton.limi;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.suelliton.limi.adapters.DietaAdapter;
import com.example.suelliton.limi.adapters.RacaoAdapter;
import com.example.suelliton.limi.models.Dieta;
import com.example.suelliton.limi.models.Racao;
import com.example.suelliton.limi.utils.MeuRecyclerViewClickListener;
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

public class RacaoActivity extends AppCompatActivity {
    public static List<Racao> RACOES;
    RacaoAdapter racaoAdapter;
    RecyclerView recyclerRacoes;
    FirebaseDatabase database;
    DatabaseReference usuarioReference;
    DatabaseReference RootReference;
    List<Racao> listaRacoes;
    String nomeDieta;
    String nomeUsuario;
    FloatingActionButton btnAdicionarRacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.racao_activity);
        database = MyDatabaseUtil.getDatabase();
        usuarioReference = database.getReference("usuarios");
        RootReference = database.getReference();
        final Bundle bundle = getIntent().getExtras();
        nomeDieta = bundle.getString("nomeDieta");
        nomeUsuario = bundle.getString("nomeUsuario");
        TextView tv1 = (TextView) findViewById(R.id.text1);
        TextView tv2 = (TextView) findViewById(R.id.text2);
        tv1.setText(nomeUsuario);
        tv2.setText(nomeDieta);

        btnAdicionarRacao = (FloatingActionButton) findViewById(R.id.fab_add_racao);
        btnAdicionarRacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("nomeDieta",nomeDieta);
                startActivity(new Intent(RacaoActivity.this,AdicionaRacao.class).putExtras(bundle));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        preecheRecycler();
    }

    public void preecheRecycler(){

        for (Dieta d: DIETAS ) {
            if(d.getNome().equals(nomeDieta)){
                RACOES = d.getRacoes();
            }
        }

        racaoAdapter= new RacaoAdapter(this, RACOES,nomeUsuario,nomeDieta);
        recyclerRacoes = (RecyclerView) findViewById(R.id.recycler_racoes);
        recyclerRacoes.setAdapter(racaoAdapter);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerRacoes.setLayoutManager(layout);



    }




}
