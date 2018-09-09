package com.example.suelliton.limi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suelliton.limi.R;
import com.example.suelliton.limi.models.Dia;
import com.example.suelliton.limi.models.Experimento;
import com.example.suelliton.limi.utils.MyDatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.suelliton.limi.activities.ListExperimento.logadoReference;
import static com.example.suelliton.limi.activities.Splash.LOGADO;
import static com.example.suelliton.limi.adapters.ExperimentoAdapter.experimentoClicado;


public class AddDados extends AppCompatActivity {
    final String listaSpinner[] = {"Racao 1","Racao 2","Racao 3","Racao4","Racao 5"};
    Spinner spinner;
    Button btnSalvarDados;
    String racao ;
    EditText ed_adicionado;
    EditText ed_sobra;
    int qtdAnimais;
    String nomeEspecie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dados_activity);
        findViews();
        setViewListeners();
        inflaAnimais();
    }
    public void findViews(){
        spinner = (Spinner) findViewById(R.id.spinner);
        ed_adicionado = (EditText) findViewById(R.id.adicionado);
        ed_sobra = (EditText) findViewById(R.id.sobra);
        btnSalvarDados = (Button) findViewById(R.id.btn_salvar_dados);
    }
    public void setViewListeners(){
        ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_spinner_item,listaSpinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                racao = listaSpinner[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSalvarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float adicionado = Float.parseFloat(ed_adicionado.getText().toString());
                float sobra = Float.parseFloat(ed_sobra.getText().toString());
                salvarDados(racao,adicionado,sobra);
            }
        });
    }
    public void inflaAnimais(){
        Query query = logadoReference.child("experimentos").child(experimentoClicado);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Experimento experimento = dataSnapshot.getValue(Experimento.class);

                LinearLayout horizontal = LinearLayout.class.cast(findViewById(R.id.linear_horizontal));
                qtdAnimais = experimento.getQtd_animais();
                nomeEspecie = experimento.getEspecie();
                for (int i=1; i<= qtdAnimais;i++) {
                    View view = getLayoutInflater().inflate(R.layout.inflate_animais,horizontal,false);
                    TextView txtItem = view.findViewById(R.id.tv);
                    TextView edtItem = view.findViewById(R.id.ed);
                    txtItem.setId(i+qtdAnimais);
                    edtItem.setId(i);//começa da qtsanimais, para diferenciar dos ids do textview
                    txtItem.setText(experimento.getEspecie()+" "+i);
                    edtItem.setHint("peso");
                    horizontal.addView(view);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

    public void salvarDados(String racao, float adicionado,float sobra){
        String data = getDataAtual();
        float consumido = adicionado - sobra;
        float percentualConsumido = (100*consumido)/adicionado;
        String dataCompleta = getDataAtual();
        int d = Integer.parseInt(dataCompleta.split("-")[2]);
        int m = Integer.parseInt(dataCompleta.split("-")[1]);
        int a = Integer.parseInt(dataCompleta.split("-")[0]);

        Dia dia = new Dia(racao,adicionado,sobra,consumido,percentualConsumido,d,m,a);

        logadoReference.child("experimentos").child(experimentoClicado).child("dados").child(dataCompleta).setValue(dia);
        for(int i = 1;i <= qtdAnimais;i++){
            EditText editText = (EditText) findViewById(i);
            float pesoAnimal= Float.parseFloat(editText.getText().toString());
            String nomeAnimal = nomeEspecie+" "+i;
            logadoReference.child("experimentos").child(experimentoClicado).child("dados").child(dataCompleta).child("pesagem").child(nomeAnimal).setValue(pesoAnimal);

        }
        Toast.makeText(this, "Dados adicionados com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }

    public String getDataAtual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String data_completa = dateFormat.format(data_atual);
        String hora_atual = dateFormat_hora.format(data_atual);
        return data_completa;
    }


}
