package com.example.suelliton.limi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suelliton.limi.R;
import com.example.suelliton.limi.models.Experimento;
import com.example.suelliton.limi.utils.MyDatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.suelliton.limi.activities.ListExperimento.logadoReference;
import static com.example.suelliton.limi.activities.Splash.LOGADO;

public class AddExperimento extends AppCompatActivity {
    Button btnSalvarDieta;
    EditText ed_nome;
    EditText ed_descricao;
    EditText ed_especie;
    EditText ed_qtdAnimais;
    String nome;
    String descricao;
    String especie;
    int qtdAnimais;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_experimento_activity);
        findViews();
        setViewListeners();
    }
    public void findViews(){
        ed_nome = (EditText) findViewById(R.id.nome);
        ed_descricao = (EditText) findViewById(R.id.descricao);
        ed_especie = (EditText) findViewById(R.id.especie);
        ed_qtdAnimais = (EditText) findViewById(R.id.qtd_animais);
        btnSalvarDieta = (Button) findViewById(R.id.btn_salvar_dieta);
    }
    public void setViewListeners(){
        btnSalvarDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarExperimento();
            }
        });
    }

    public void salvarExperimento(){
        nome = ed_nome.getText().toString();
        descricao = ed_descricao.getText().toString();
        especie = ed_especie.getText().toString();
        qtdAnimais = Integer.parseInt(ed_qtdAnimais.getText().toString());

        if(nome.equals("") || descricao.equals("") || especie.equals("")  || qtdAnimais == 0){
            Toast.makeText(this, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
        }else{
            Query query = logadoReference.child("experimentos").child(nome);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Experimento experimento = dataSnapshot.getValue(Experimento.class);
                    if(experimento == null){//se não tiver experimento com mesmo nome
                        enviarFirebase(nome,descricao,especie,qtdAnimais);
                    }else{
                        Toast.makeText(AddExperimento.this, "Dieta já cadastrada, tente outro nome", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    public void enviarFirebase(String nome, String descricao, String especie, int qtdAnimais){
        Experimento experimento = new Experimento(nome,descricao,especie,qtdAnimais);
        logadoReference.child("experimentos").child(experimento.getNome()).setValue(experimento);
        Toast.makeText(AddExperimento.this, "Experimento salvo com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }


}
