package com.example.suelliton.limi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.suelliton.limi.models.Usuario;
import com.example.suelliton.limi.utils.MyDatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdicionarColaborador extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference RootReference;
    ValueEventListener listener;
    Button btnPesquisar;
    Button btnAdicionar;
    EditText edPesquisa;
    TextView tvResultado;
    String colaborador;
    List<Usuario> listaUsuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_colaborador);
        database = MyDatabaseUtil.getDatabase();
        RootReference = database.getReference("usuarios");
        btnPesquisar = (Button) findViewById(R.id.btn_pesquisar_colaborador);
        btnAdicionar = (Button) findViewById(R.id.btn_adicionar_colaborador);
        btnAdicionar.setVisibility(View.INVISIBLE);
        edPesquisa = (EditText) findViewById(R.id.pesquisa);
        tvResultado = (TextView) findViewById(R.id.resultado);

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Usuario usuario : listaUsuarios) {
                    if(usuario.getUsername().equals(edPesquisa.getText().toString())){
                        tvResultado.setText(usuario.getUsername());
                        btnAdicionar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        carregaInformacoes();
    }
    public void carregaInformacoes(){
        listaUsuarios = new ArrayList<>();

        listener = RootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaUsuarios.removeAll(listaUsuarios);
                if(dataSnapshot.exists()){
                    for (DataSnapshot u:dataSnapshot.getChildren()) {
                        Usuario usuario = u.getValue(Usuario.class);
                        listaUsuarios.add(usuario);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
