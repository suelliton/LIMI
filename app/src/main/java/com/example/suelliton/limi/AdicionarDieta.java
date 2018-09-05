package com.example.suelliton.limi;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suelliton.limi.models.Dieta;
import com.example.suelliton.limi.models.Usuario;
import com.example.suelliton.limi.utils.MyDatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.suelliton.limi.Splash.LOGADO;

public class AdicionarDieta extends AppCompatActivity {
    Button btnSalvarDieta;
    EditText ed_nome;
    EditText ed_descricao;
    FirebaseDatabase database;
    DatabaseReference RootReference;
    ValueEventListener listener;
    List<Dieta> listaDietas;
    boolean existe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_dieta);
        database = MyDatabaseUtil.getDatabase();
        RootReference = database.getReference("usuarios");
        boolean existe = false;
        ed_nome = (EditText) findViewById(R.id.nome);
        ed_descricao = (EditText) findViewById(R.id.descricao);
        btnSalvarDieta = (Button) findViewById(R.id.btn_salvar_dieta);

        btnSalvarDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDieta();
            }
        });
        listaDietas = new ArrayList<>();

        listener = RootReference.child(LOGADO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaDietas.removeAll(listaDietas);
                if (dataSnapshot.exists()) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    if(usuario.getDietas() != null) {
                        for (Dieta d : usuario.getDietas()) {
                            listaDietas.add(d);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void salvarDieta(){
        String nome = ed_nome.getText().toString();
        String descricao = ed_descricao.getText().toString();

        if(nome.equals("") || descricao.equals("")){
            Toast.makeText(this, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
        }else{
            validaRegistro(nome);
            for (Dieta d: listaDietas) {
                if(d.getNome().equals(nome)){
                    existe = true;
                }
            }
            if(!existe){
                Dieta dieta = new Dieta(nome, descricao);
                salvaNoFirebase(dieta);

            }else{
                Toast.makeText(this, "Dieta já cadastrada, tente outro nome", Toast.LENGTH_SHORT).show();
            }
        }



    }
    public void salvaNoFirebase(Dieta dieta){
        listaDietas.add(dieta);
        RootReference.child(LOGADO).child("dietas").setValue(listaDietas);
        Toast.makeText(AdicionarDieta.this, "Dieta salva com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void validaRegistro(final String nome) {


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(listener != null){
            RootReference.removeEventListener(listener);
        }
    }
}
