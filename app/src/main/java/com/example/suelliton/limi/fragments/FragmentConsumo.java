package com.example.suelliton.limi.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suelliton.limi.R;
import com.example.suelliton.limi.models.Coleta;
import com.example.suelliton.limi.utils.DayAxisValueFormatter;
import com.example.suelliton.limi.utils.MyAxisValueFormatter;
import com.example.suelliton.limi.utils.XYMarkerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.suelliton.limi.activities.Experimento.logadoReference;
import static com.example.suelliton.limi.adapters.ExperimentoAdapter.experimentoClicado;

public class FragmentConsumo extends Fragment {
    List<Coleta> listaColetas;
    float[] soma = new float[11];

    int[] cont = new int[11];
    protected BarChart mChart;

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.consumo_fragment, container, false);
        for(int i=0;i < 11; i++){
            soma[i] = 0;
        }

        loadDados();
        return v;
    }



    public void loadDados(){
        listaColetas = new ArrayList<>();

        Query query = logadoReference.child("experimentos").child(experimentoClicado).child("dados");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaColetas.removeAll(listaColetas);
                for (DataSnapshot data:dataSnapshot.getChildren() ) {
                    Coleta coleta = data.getValue(Coleta.class);
                    if(coleta.getRacao().equals("Padrão")){
                        soma[0] = soma[0] + coleta.getPercentualConsumido();
                        cont[0] = cont[0]+1;
                    }else if(coleta.getRacao().equals("Low Carb")){
                        soma[1] = soma[1] + coleta.getPercentualConsumido();
                        cont[1] = cont[1]+1;
                    }else if(coleta.getRacao().equals("Low Fat")){
                        soma[2] = soma[2] + coleta.getPercentualConsumido();
                        cont[2] = cont[2]+1;
                    }else if(coleta.getRacao().equals("Low Prot")){
                        soma[3] = soma[3] + coleta.getPercentualConsumido();
                        cont[3] = cont[3]+1;
                    }else if(coleta.getRacao().equals("Low Prot/Carb/Fat")){
                        soma[4] = soma[4] + coleta.getPercentualConsumido();
                        cont[4] = cont[4]+1;
                    }else if(coleta.getRacao().equals("Low Carb/Fat")){
                        soma[5] = soma[5] + coleta.getPercentualConsumido();
                        cont[5] = cont[5]+1;
                    }else if(coleta.getRacao().equals("High Carb")){
                        soma[6] = soma[6] + coleta.getPercentualConsumido();
                        cont[6] = cont[6]+1;
                    }else if(coleta.getRacao().equals("High Fat")){
                        soma[7] = soma[7] + coleta.getPercentualConsumido();
                        cont[7] = cont[7]+1;
                    }else if(coleta.getRacao().equals("High Prot")){
                        soma[8] = soma[8] + coleta.getPercentualConsumido();
                        cont[8] = cont[8]+1;
                    }else if(coleta.getRacao().equals("High Prot/Carb/Fat")){
                        soma[9] = soma[9] + coleta.getPercentualConsumido();
                        cont[9] = cont[9]+1;
                    }else if(coleta.getRacao().equals("High Carb/Fat")){
                        soma[10] = soma[10] + coleta.getPercentualConsumido();
                        cont[10] = cont[10]+1;
                    }


                    listaColetas.add(coleta);
                    Log.i("listadia","racao "+ coleta.getRacao());
                }
                float[] r = new float[11];
                for(int i=0;i<cont.length;i++) {
                    if (cont[i] == 0) {
                        cont[i] =  1;
                    }
                }
                r[0] = soma[0]/cont[0];
                r[1] = soma[1]/cont[1];
                r[2] = soma[2]/cont[2];
                r[3] = soma[3]/cont[3];
                r[4] = soma[4]/cont[4];
                r[5] = soma[5]/cont[5];
                r[6] = soma[6]/cont[6];
                r[7] = soma[7]/cont[7];
                r[8] = soma[8]/cont[8];
                r[9] = soma[9]/cont[9];
                r[10] = soma[10]/cont[10];

                cont = new int[]{0, 0, 0,0,0,0,0,0,0,0,0};
                carregaGrafico(r);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void carregaGrafico(float[] r){

        mChart = v.findViewById(R.id.grafico_barras);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(100);
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
        xAxis.setLabelCount(11);

        final List list_x_axis_name = new ArrayList<>();
        list_x_axis_name.add("Padrão");
        list_x_axis_name.add("LC");
        list_x_axis_name.add("LF");
        list_x_axis_name.add("LP");
        list_x_axis_name.add("LPCF");
        list_x_axis_name.add("LCF");
        list_x_axis_name.add("HC");
        list_x_axis_name.add("HF");
        list_x_axis_name.add("HP");
        list_x_axis_name.add("HPCF");
        list_x_axis_name.add("HCF");

        xAxis.setValueFormatter(new IAxisValueFormatter() {

    public String getFormattedValue(float value, AxisBase axis) {
        if (value >= 0) {
           if (value <= list_x_axis_name.size() - 1) {
            return (String) list_x_axis_name.get((int) value);
           }
           return "";

        }

        return "";

        }
        });


        xAxis.setEnabled(true);
        //xAxis.setValueFormatter(xAxisFormatter);
        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(10, false);
        leftAxis.setValueFormatter(new PercentFormatter());
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        //rightAxis.setDrawGridLines(false);
        //rightAxis.setTypeface(mTfLight);
        //rightAxis.setLabelCount(0, false);
        //rightAxis.setValueFormatter(new PercentFormatter());
        //rightAxis.setSpaceTop(15f);
        //rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(8f);
        l.setTextSize(7f);
        l.setXEntrySpace(1f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        /*
        LegendEntry leg1 =  new LegendEntry();
        LegendEntry leg2 =  new LegendEntry();
        LegendEntry leg3 =  new LegendEntry();
        LegendEntry leg4 =  new LegendEntry();
        LegendEntry leg5 =  new LegendEntry();
        LegendEntry leg6 =  new LegendEntry();
        LegendEntry leg7 =  new LegendEntry();
        LegendEntry leg8 =  new LegendEntry();
        LegendEntry leg9 =  new LegendEntry();
        LegendEntry leg10 =  new LegendEntry();
        LegendEntry leg11 =  new LegendEntry();
        leg1.label = "Padrão";
        leg2.label = "L C";
        leg3.label = "L F";
        leg4.label = "L P";
        leg5.label = "L PCF";
        leg6.label = "L CF";
        leg7.label = "H C";
        leg8.label = "H F";
        leg9.label = "H P" ;
        leg10.label = "H PCF" ;
        leg11.label = "H CF" ;

        List<LegendEntry> legendas = new ArrayList<>();
        legendas.add(leg1);legendas.add(leg2);legendas.add(leg3);legendas.add(leg4);
        legendas.add(leg5);legendas.add(leg6);legendas.add(leg7);legendas.add(leg8);
        legendas.add(leg9);legendas.add(leg10);legendas.add(leg11);
        l.setCustom(legendas);*/

        XYMarkerView mv = new XYMarkerView(v.getContext(), xAxisFormatter);
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
            set1 = new BarDataSet(yVals1, "Dietas");

            set1.setDrawIcons(false);


            int startColor1 = ContextCompat.getColor(v.getContext(), android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(v.getContext(), android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(v.getContext(), android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(v.getContext(), android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(v.getContext(), android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(v.getContext(), android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(v.getContext(), android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(v.getContext(), android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(v.getContext(), android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(v.getContext(), android.R.color.holo_orange_dark);
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
