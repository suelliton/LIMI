package com.example.suelliton.limi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.suelliton.limi.adapters.DietaAdapter;
import com.example.suelliton.limi.models.Colabora;
import com.example.suelliton.limi.models.Dieta;
import com.example.suelliton.limi.models.ItemColabora;
import com.example.suelliton.limi.models.Usuario;
import com.example.suelliton.limi.utils.MyDatabaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.suelliton.limi.Splash.LOGADO;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerDietas;
    FirebaseDatabase database;
    DatabaseReference usuarioReference;
    DatabaseReference RootReference;
    ValueEventListener listener;
    DietaAdapter dietaAdapter;
    Usuario usuarioLogado;
    List<ItemColabora> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        database = MyDatabaseUtil.getDatabase();
        usuarioReference = database.getReference("usuarios");
        RootReference = database.getReference();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabAddDieta = (FloatingActionButton) findViewById(R.id.fab_add_dieta);
        fabAddDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Principal.this,AdicionarDieta.class));
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Toast.makeText(this, "Usuario logado: "+LOGADO, Toast.LENGTH_SHORT).show();

        preecheRecycler();

    }

    @Override
    protected void onStart() {
        super.onStart();
        preecheRecycler();
    }

    public void preecheRecycler(){
        final List<Dieta> listaDietas = new ArrayList<>();
        dietaAdapter= new DietaAdapter(this, listaDietas);
        recyclerDietas = (RecyclerView) findViewById(R.id.recycler_dietas);
        recyclerDietas.setAdapter(dietaAdapter);

        Query query = usuarioReference.child(LOGADO).child("dietas").limitToFirst(20);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Dieta d = data.getValue(Dieta.class);
                        listaDietas.add(d);
                    }
                    dietaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Query query2 = usuarioReference.child(LOGADO).orderByKey();
        itens = new ArrayList<>();
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itens.removeAll(itens);
                if(dataSnapshot.exists()) {
                    usuarioLogado = dataSnapshot.getValue(Usuario.class);
                    if(usuarioLogado != null){
                        Toast.makeText(Principal.this, "Entrou na query 2 "+dataSnapshot.getValue(Usuario.class).getEmail(), Toast.LENGTH_SHORT).show();
                        if(usuarioLogado.getColabora()!=null) {
                            Colabora colabora = usuarioLogado.getColabora();
                            itens = colabora.getUsuarios();
                            Log.i("teste", "tamanho itens " + itens.size());
                            for (ItemColabora i : itens) {
                                Log.i("teste", i.getChaveUsuario());
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
                for (ItemColabora item:itens ) {
                    Query queryItem = usuarioReference.child(item.getChaveUsuario()).child("dietas").orderByKey().limitToFirst(20);
                    queryItem.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    Dieta d = data.getValue(Dieta.class);
                                    listaDietas.add(d);
                                }
                                dietaAdapter.notifyDataSetChanged();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        },2000);





        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerDietas.setLayoutManager(layout);



    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_adicionar_colaborador) {
            startActivity(new Intent(Principal.this,AdicionarColaborador.class));

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("usuarioLogado", "");
            editor.apply();
            LOGADO="";
            //USUARIO_OBJETO_LOGADO = null;
            startActivity(new Intent(Principal.this,Login.class));
            finish();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(listener != null){
            usuarioReference.removeEventListener(listener);
        }
    }
}
