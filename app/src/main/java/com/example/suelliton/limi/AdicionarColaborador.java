package com.example.suelliton.limi;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.suelliton.limi.models.Colabora;
import com.example.suelliton.limi.models.Dieta;
import com.example.suelliton.limi.models.ItemColabora;
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

public class AdicionarColaborador extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference RootReference;
    DatabaseReference usuarioReference;
    ValueEventListener listener;
    ValueEventListener listenerColaboradores;
    Button btnPesquisar;
    Button btnAdicionar;
    EditText edPesquisa;
    TextView tvResultado;
    String aColaborar;
    boolean colaboradorExiste;//guarda se o colaborador existe na lista
    List<Usuario> listaUsuarios;
    List<Colabora> listaColaboradores;
    Colabora colabora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_colaborador);
        database = MyDatabaseUtil.getDatabase();
        usuarioReference = database.getReference("usuarios");
        RootReference = database.getReference();
        btnPesquisar = (Button) findViewById(R.id.btn_pesquisar_colaborador);
        btnAdicionar = (Button) findViewById(R.id.btn_adicionar_colaborador);
        btnAdicionar.setVisibility(View.INVISIBLE);
        edPesquisa = (EditText) findViewById(R.id.pesquisa);
        tvResultado = (TextView) findViewById(R.id.resultado);
        listaUsuarios = new ArrayList<>();
        carregaInformacoes();
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = usuarioReference.orderByChild("username").limitToFirst(1);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);
                        if(usuario != null){
                            tvResultado.setText(edPesquisa.getText().toString());
                            btnAdicionar.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colaboradorExiste = false;
                aColaborar = edPesquisa.getText().toString();

                Query query = usuarioReference.child(aColaborar).child("colabora").limitToFirst(20);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            colabora = dataSnapshot.getValue(Colabora.class);
                            if(colabora != null){
                                for (ItemColabora item: colabora.getUsuarios()  ) {//verifica se o usuario ja esta na lista de "patrao" do outro usuario
                                    if(item.getChaveUsuario().equals(LOGADO)){
                                        colaboradorExiste = true;
                                    }
                                }
                            }


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Handler handler =  new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(colaboradorExiste){

                        }else{
                            ItemColabora novoItem = new ItemColabora(LOGADO);
                            List<ItemColabora> itens= new ArrayList<>();
                            if(colabora != null) {
                                itens = colabora.getUsuarios();
                            }else{
                                colabora = new Colabora();
                            }
                            itens.add(novoItem);
                            colabora.setUsuarios(itens);
                            usuarioReference.child(aColaborar).child("colabora").setValue(colabora);
                            finish();
                        }
                    }
                },1000);


            }
        });

    }
    public void carregaInformacoes(){


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
