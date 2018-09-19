package com.example.suelliton.limi.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.suelliton.limi.R;
import com.example.suelliton.limi.models.Animal;
import com.example.suelliton.limi.models.Coleta;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.suelliton.limi.activities.Experimento.logadoReference;
import static com.example.suelliton.limi.adapters.ExperimentoAdapter.experimentoClicado;

public class FragmentPeso extends Fragment {
    List<Float> listaPesosMacho;
    List<Float> listaPesosFemea;
    List<Coleta> listaColetas;
    private LineChart mChart;
    View v;
    RadioButton radio_padrao;
    RadioButton radio_lc;
    RadioButton radio_lf;
    RadioButton radio_lp;
    RadioButton radio_lpcf;
    RadioButton radio_lcf;
    RadioButton radio_hc;
    RadioButton radio_hf;
    RadioButton radio_hp;
    RadioButton radio_hpcf;
    RadioButton radio_hcf;
    String racaoEscolhida = "Padrão";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.peso_fragment, container, false);
        listaPesosFemea = new ArrayList<>();
        listaPesosMacho = new ArrayList<>();
        listaColetas =  new ArrayList<>();

        loadListas();
        findViews();
        setListeners();


        return v;
    }
    public void findViews(){
        radio_padrao = (RadioButton) v.findViewById(R.id.radio_padrao);
        radio_lc = (RadioButton) v.findViewById(R.id.radio_lc);
        radio_lf = (RadioButton) v.findViewById(R.id.radio_lf);
        radio_lp = (RadioButton) v.findViewById(R.id.radio_lp);
        radio_lpcf = (RadioButton) v.findViewById(R.id.radio_lpcf);
        radio_lcf = (RadioButton) v.findViewById(R.id.radio_lcf);
        radio_hc = (RadioButton) v.findViewById(R.id.radio_hc);
        radio_hf = (RadioButton) v.findViewById(R.id.radio_hf);
        radio_hp = (RadioButton) v.findViewById(R.id.radio_hp);
        radio_hpcf = (RadioButton) v.findViewById(R.id.radio_hpcf);
        radio_hcf = (RadioButton) v.findViewById(R.id.radio_hcf);



    }

    public void setListeners(){
            RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radio_group);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.radio_padrao:
                            racaoEscolhida = "Padrão";
                            loadListas();
                            break;
                        case R.id.radio_lc:
                            racaoEscolhida = "Low Carb";
                            loadListas();
                            break;
                        case R.id.radio_lf:
                            racaoEscolhida = "Low Fat";
                            loadListas();
                            break;
                        case R.id.radio_lp:
                            racaoEscolhida = "Low Prot";
                            loadListas();
                            break;
                        case R.id.radio_lpcf:
                            racaoEscolhida = "Low Prot/Carb/Fat";
                            loadListas();
                            break;
                        case R.id.radio_lcf:
                            racaoEscolhida = "Low Carb/Fat";
                            loadListas();
                            break;
                        case R.id.radio_hc:
                            racaoEscolhida = "High Carb";
                            loadListas();
                            break;
                        case R.id.radio_hf:
                            racaoEscolhida = "High Fat";
                            loadListas();
                            break;
                        case R.id.radio_hp:
                            racaoEscolhida = "High Prot";
                            loadListas();
                            break;
                        case R.id.radio_hpcf:
                            racaoEscolhida = "High Prot/Carb/Fat";
                            loadListas();
                            break;
                        case R.id.radio_hcf:
                            racaoEscolhida = "High Carb/Fat";
                            loadListas();
                            break;
                    }
                }
            });


    }

    public void loadListas(){
        listaPesosFemea.removeAll(listaPesosFemea);
        listaPesosMacho.removeAll(listaPesosMacho);
        Log.i("pesos","chegou!");
        Query query = logadoReference.child("experimentos").child(experimentoClicado).child("dados").orderByChild("racao").equalTo(racaoEscolhida).limitToFirst(100);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    Coleta coleta = data.getValue(Coleta.class);
                    int contF = 0 ;
                    int contM  = 0;
                    float valoresF = 0;
                    float valoresM = 0;
                    for (Animal a :  coleta.getPesagem() ) {
                        if(a.getSexo().equals("macho")){
                            valoresM = valoresM + a.getPeso();
                            contM = contM+1;
                        }else{
                            valoresF = valoresF + a.getPeso();
                            contF = contF +1;
                        }
                    }
                    if(contF == 0) contF = 1;
                    if(contM == 0) contM = 1;
                    listaPesosMacho.add(valoresM/contM);
                    listaPesosFemea.add(valoresF/contF);
                }
                if(listaPesosFemea.size()>0 || listaPesosMacho.size() >0) {
                    carregaGrafico();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void carregaGrafico(){


        mChart = v.findViewById(R.id.chart1);
        mChart.setViewPortOffsets(20, 0, 25, 0);
        mChart.setBackgroundColor(Color.WHITE);
        // no description text
        mChart.getDescription().setEnabled(false);
        // enable touch gestures
        mChart.setTouchEnabled(true);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.setMaxHighlightDistance(300);

        XAxis x = mChart.getXAxis();
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

        x.setCenterAxisLabels(false);
        x.setTextSize(10f);
        if(listaPesosMacho.size() < 15){
            x.setLabelCount(listaPesosMacho.size(),true);
        }else{
            x.setLabelCount(15);
        }
        x.setTextColor(Color.BLACK);
        x.setDrawAxisLine(true);
        x.setDrawGridLines(true);

        YAxis y = mChart.getAxisLeft();

        y.setLabelCount(10, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        //y.setAxisLineColor(Color.argb(255, 0, 255, 136));

        mChart.getAxisRight().setEnabled(false);

        setData(listaPesosMacho, listaPesosFemea);


        Legend l = mChart.getLegend();
        l.setEnabled(false);

        //mChart.animateXY(2000, 2000);
        //mChart.animateY(2000);
        //mChart.animateX(500);

/*
        List<ILineDataSet> sets = mChart.getData().getDataSets();

        for (ILineDataSet iSet : sets) {

            LineDataSet set = (LineDataSet) iSet;
            set.setColor(Color.argb(255, 0, 255, 136));
            set.setDrawFilled(true);
            set.setDrawValues(true);
            set.setCircleColor(Color.argb(255, 0, 255, 136));
        }*/


        // dont forget to refresh the drawing
        mChart.invalidate();
    }


    private void setData(List<Float> masculino, List<Float> feminino) {

        ArrayList<Entry> yValsM = new ArrayList<Entry>();

        for (int i = 0; i < masculino.size(); i++) {
           yValsM.add(new Entry(i+1, (float) masculino.get(i)));
        }

        ArrayList<Entry> yValsF = new ArrayList<Entry>();

        for (int i = 0; i < feminino.size(); i++) {
            yValsF.add(new Entry(i+1, (float) feminino.get(i)));
        }


        LineDataSet set1;
        LineDataSet set2;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            if(masculino.size() > 0) {
                set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
                set1.setValues(yValsM);
            }
            if(feminino.size() > 0) {
                set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
                set2.setValues(yValsF);
            }
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yValsM, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            //set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.argb(255,30,144,255));
            set1.setFillColor(Color.WHITE);//cor embaixo da linha
            //set1.setFillAlpha(100);//transparencia embaixo da linha
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            set2 = new LineDataSet(yValsF, "DataSet 2");

            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set2.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set2.setDrawCircles(false);
            set2.setLineWidth(1.8f);
            set2.setCircleRadius(4f);
            set2.setCircleColor(Color.WHITE);
            //set2.setHighLightColor(Color.rgb(244, 117, 117));
            set2.setColor(Color.argb(255, 255, 20, 147));
            set2.setFillColor(Color.WHITE);//cor embaixo da linha
            //set2.setFillAlpha(100);//transparencia embaixo da linha
            set2.setDrawHorizontalHighlightIndicator(false);
            set2.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            // create a data object with the datasets
            LineData data = new LineData(set1,set2);

            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);


        }
    }

    }


