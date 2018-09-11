package com.example.suelliton.limi.activities;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.suelliton.limi.R;
import com.example.suelliton.limi.models.Dia;
import com.example.suelliton.limi.utils.DayAxisValueFormatter;
import com.example.suelliton.limi.utils.MyAxisValueFormatter;
import com.example.suelliton.limi.utils.MyDatabaseUtil;
import com.example.suelliton.limi.utils.XYMarkerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.database.ChildEventListener;
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
import static com.example.suelliton.limi.adapters.ExperimentoAdapter.experimentoClicado;


public class ShowGrafico extends AppCompatActivity {

    List<Dia> listaDias;
    float somaR1 = 0;
    float somaR2 = 0;
    float somaR3 = 0;
    float somaR4 = 0;
    float somaR5 = 0;
    int[] cont = new int[5];
    protected BarChart mChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_grafico_activity);





        loadDados();


    }

    public void loadDados(){
        listaDias = new ArrayList<>();

        Query query = logadoReference.child("experimentos").child(experimentoClicado).child("dados");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaDias.removeAll(listaDias);
                for (DataSnapshot data:dataSnapshot.getChildren() ) {
                    Dia dia = data.getValue(Dia.class);
                    if(dia.getRacao().equals("Racao 1")){
                        somaR1 = somaR1 + dia.getPercentualConsumido();
                        cont[0] = cont[0]+1;
                    }else if(dia.getRacao().equals("Racao 2")){
                        somaR2 = somaR2 + dia.getPercentualConsumido();
                        cont[1] = cont[1]+1;
                    }else if(dia.getRacao().equals("Racao 3")){
                        somaR3 = somaR3 + dia.getPercentualConsumido();
                        cont[2] = cont[2]+1;
                    }else if(dia.getRacao().equals("Racao 4")){
                        somaR4 = somaR4 + dia.getPercentualConsumido();
                        cont[3] = cont[3]+1;
                    }else if(dia.getRacao().equals("Racao 5")){
                        somaR5 = somaR5 + dia.getPercentualConsumido();
                        cont[4] = cont[4]+1;
                    }

                    listaDias.add(dia);
                    Log.i("listadia","racao "+dia.getRacao());
                }
                float[] r = new float[5];
                for(int i=0;i<cont.length;i++) {
                    if (cont[i] == 0) {
                        cont[i] =  1;
                    }
                }
                r[0] = somaR1/cont[0];
                r[1] = somaR2/cont[1];
                r[2] = somaR3/cont[2];
                r[3] = somaR4/cont[3];
                r[4] = somaR5/cont[4];
                for(int i=0;i<5;i++){
                    Log.i("c",cont[i]+"");
                }
                cont = new int[]{0, 0, 0, 0, 0};
                carregaGrafico(r);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void carregaGrafico(float[] r){

        mChart = findViewById(R.id.grafico_barras);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        //mChart.setDrawYLabels(false);
        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);
        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        // rightAxis.setTypeface(mTfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        setData(r);



    }



    private void setData(float[] r) {

        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i <  r.length; i++) {

            yVals1.add(new BarEntry(i, r[i]));

        }

        BarDataSet set1;


        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year 2017");

            set1.setDrawIcons(false);


            int startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);
            int[] cores = {startColor2,startColor3,startColor4,startColor5};
            set1.setColors(cores);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);
            mChart.setData(data);
        }
    }


    }





