package com.example.suelliton.limi.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.suelliton.limi.DadosActivity;
import com.example.suelliton.limi.R;
import com.example.suelliton.limi.models.Racao;

import java.util.List;

public class RacaoAdapter extends RecyclerView.Adapter {
    Context context;
    List<Racao> listaRacoes;
    String nomeUsuario;
    String nomeDieta;

    public RacaoAdapter(Context c, List<Racao> lista,String usuario,String dieta){
        this.context = c;
        this.listaRacoes = lista;
        this.nomeUsuario = usuario;
        this.nomeDieta = dieta;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.inflate_racoes, parent, false);

        RacaoViewHolder holder = new RacaoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RacaoViewHolder racaoholder = (RacaoViewHolder) holder;
        final Racao racaoEscolhida = listaRacoes.get(position);

        racaoholder.nome.setText(racaoEscolhida.getNome());

        racaoholder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("nomeRacao",racaoEscolhida.getNome());
                bundle.putString("nomeUsuario",nomeUsuario);
                bundle.putString("nomeDieta",nomeDieta);
                Intent intent = new Intent(view.getContext(),DadosActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return  listaRacoes == null ? 0 :  listaRacoes.size();
    }

    public class RacaoViewHolder extends RecyclerView.ViewHolder {
        final TextView nome;
        final LinearLayout row;
        public RacaoViewHolder(View v) {
            super(v);
            nome = v.findViewById(R.id.label_nome);
            row = v.findViewById(R.id.row);
        }
    }
}