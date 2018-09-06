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

import com.example.suelliton.limi.R;
import com.example.suelliton.limi.RacaoActivity;
import com.example.suelliton.limi.models.Dieta;

import java.util.List;

public class DietaAdapter extends RecyclerView.Adapter {
    Context context;
    List<Dieta> listaDietas;

    public DietaAdapter(Context c, List<Dieta> lista){
        this.context = c;
        this.listaDietas = lista;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.inflate_dietas, parent, false);

        DietaViewHolder holder = new DietaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DietaViewHolder dietaholder = (DietaViewHolder) holder;
        final Dieta dietaEscolhida = listaDietas.get(position);

        dietaholder.nome.setText(dietaEscolhida.getNome());
        dietaholder.descricao.setText(dietaEscolhida.getDescricao());

        dietaholder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("nomeDieta",dietaEscolhida.getNome());
                bundle.putString("nomeUsuario",dietaEscolhida.getUsuario());
                Intent intent = new Intent(view.getContext(),RacaoActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return  listaDietas == null ? 0 :  listaDietas.size();
    }

    public class DietaViewHolder extends RecyclerView.ViewHolder {
        final TextView nome;
        final TextView descricao;
        final LinearLayout row;
        public DietaViewHolder(View v) {
            super(v);
            nome = v.findViewById(R.id.label_nome);
            descricao = v.findViewById(R.id.label_descricao);
            row = v.findViewById(R.id.row);
        }
    }
}
