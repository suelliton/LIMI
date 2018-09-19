package com.example.suelliton.limi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suelliton.limi.R;
import com.example.suelliton.limi.models.Animal;
import com.example.suelliton.limi.models.Experimento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.suelliton.limi.activities.Experimento.logadoReference;

public class AddExperimento extends AppCompatActivity {
    Button btnSalvarDieta;
    EditText ed_nome;
    EditText ed_descricao;
    EditText ed_especie;
    EditText ed_qtdAnimais;
    String nome;
    String descricao;
    String especie;
    int qtdAnimais = 0;
    Animal animal;
    List<Animal> animais;
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
        ed_especie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                especie = ed_especie.getText().toString();
            }
        });
        ed_qtdAnimais.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                LinearLayout linear = LinearLayout.class.cast(findViewById(R.id.linear_layout));
                linear.removeAllViews();
                if(!ed_qtdAnimais.getText().toString().equals("")) {
                    qtdAnimais = Integer.parseInt(ed_qtdAnimais.getText().toString());
                    for (int i = 1; i <= qtdAnimais; i++) {
                        View view = getLayoutInflater().inflate(R.layout.inflate_set_sexo_animais, linear, false);
                        TextView txtItem = view.findViewById(R.id.tv);
                        RadioGroup radioGroup = view.findViewById(R.id.radio_group);
                        radioGroup.setId(i);
                        txtItem.setId(i + 20);
                        txtItem.setText(especie + " " + i);
                        linear.addView(view);
                    }
                }
            }
        });
    }

    public void salvarExperimento(){
        nome = ed_nome.getText().toString();
        descricao = ed_descricao.getText().toString();
        animais = new ArrayList<>();
        for(int i = 1; i <= qtdAnimais;i++ ){
            animal = new Animal(especie+" "+i,"",0);
            RadioGroup radioGroup = (RadioGroup) findViewById(i);
            radioGroup.getCheckedRadioButtonId();
            if(R.id.radio_macho == radioGroup.getCheckedRadioButtonId() ){
                animal.setSexo("macho");
                animais.add(animal);
            }else if(R.id.radio_femea == radioGroup.getCheckedRadioButtonId()){
                animal.setSexo("femea");
                animais.add(animal);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        if(nome.equals("") || descricao.equals("") || especie.equals("")  || qtdAnimais == 0){
            Toast.makeText(this, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
        }else{
            Query query = logadoReference.child("experimentos").child(nome);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Experimento experimento = dataSnapshot.getValue(Experimento.class);
                    if(experimento == null){//se não tiver experimento com mesmo nome
                        enviarFirebase(nome,descricao,especie,qtdAnimais,animais);
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
    public void enviarFirebase(String nome, String descricao, String especie, int qtdAnimais,List<Animal> animais){

        Experimento experimento = new Experimento(nome,animais,descricao,especie,qtdAnimais);
        logadoReference.child("experimentos").child(experimento.getNome()).setValue(experimento);
        Toast.makeText(AddExperimento.this, "Experimento salvo com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }


}
