package com.example.suelliton.limi.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.suelliton.limi.activities.AddDados;
import com.example.suelliton.limi.activities.Detalhes;
import com.example.suelliton.limi.R;
import com.example.suelliton.limi.models.Experimento;

import java.util.List;

public class ExperimentoAdapter extends RecyclerView.Adapter {
    Context context;
    List<Experimento> listaExperimentos;
    public static String experimentoClicado;

    public ExperimentoAdapter(Context c, List<Experimento> lista){
        this.context = c;
        this.listaExperimentos = lista;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.inflate_experimento, parent, false);

        DietaViewHolder holder = new DietaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DietaViewHolder experimentoholder = (DietaViewHolder) holder;
        final Experimento experimentoEscolhido = listaExperimentos.get(position);

        experimentoholder.nome.setText(experimentoEscolhido.getNome());
        experimentoholder.descricao.setText(experimentoEscolhido.getDescricao());
        experimentoholder.especie.setText(experimentoEscolhido.getQtd_animais()+" "+experimentoEscolhido.getEspecie()+"s");
        experimentoholder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                experimentoClicado = experimentoEscolhido.getNome();
                context.startActivity(new Intent(view.getContext(),Detalhes.class));
            }
        });
        experimentoholder.fab_adicionar_dia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experimentoClicado = experimentoEscolhido.getNome();
                context.startActivity(new Intent(v.getContext(),AddDados.class));
            }
        });
    }


    @Override
    public int getItemCount() {
        return listaExperimentos  == null ? 0 :  listaExperimentos.size();
    }

    public class DietaViewHolder extends RecyclerView.ViewHolder {
        final TextView nome;
        final TextView descricao;
        final TextView especie;
        final LinearLayout row;
        final FloatingActionButton fab_adicionar_dia;
        public DietaViewHolder(View v) {
            super(v);
            nome = v.findViewById(R.id.label_nome);
            descricao = v.findViewById(R.id.label_descricao);
            especie = v.findViewById(R.id.label_especie);
            row = v.findViewById(R.id.row);
            fab_adicionar_dia = v.findViewById(R.id.fab_adicionar_dia);
        }
    }
}
