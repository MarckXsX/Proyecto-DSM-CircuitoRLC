package com.example.circuitorlc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

import Calculo.RLCParalelo;

public class Graficar extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    private GraphView funcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_graficar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        try {
            String r = intent.getStringExtra("R");
            Toast.makeText(this, "El valor de R es "+r, Toast.LENGTH_SHORT).show();
            /*double r =Double.parseDouble( intent.getStringExtra("R"));
            double l =Double.parseDouble( intent.getStringExtra("L"));
            double c =Double.parseDouble( intent.getStringExtra("C"));
            double v0 =Double.parseDouble( intent.getStringExtra("v0"));
            double i0 =Double.parseDouble( intent.getStringExtra("i0"));
            double tf =Double.parseDouble( intent.getStringExtra("tf"));
            double tiempoX, voltCorriente;
            tiempoX = -0.25;


            funcion.getViewport().setScrollable(true);
            funcion.getViewport().setScrollableY(true);

            funcion.getViewport().setScalable(false);
            funcion.getViewport().setScalableY(true);

            funcion.getViewport().setXAxisBoundsManual(true);
            funcion.getViewport().setMinX(-0.25);
            funcion.getViewport().setMinX(tf);


            RLCParalelo paralelo = new RLCParalelo();
            funcion = findViewById(R.id.grafica);
            List<Double> arrayConstantesCorriente =  paralelo.CorrienteSobreAmortiguado(r,l,c,i0,v0);
            if (arrayConstantesCorriente == null){
                funcion.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "No se puede graficar, los datos no tienen solucion unica", Toast.LENGTH_SHORT).show();
            }
            else {
                double a1 = arrayConstantesCorriente.get(0);
                double a2 = arrayConstantesCorriente.get(1);
                double s1 = arrayConstantesCorriente.get(2);
                double s2 = arrayConstantesCorriente.get(2);

                series = new LineGraphSeries<DataPoint>();
                for (int i = 0; i < 500; i++){
                    tiempoX += 0.01;
                    voltCorriente = funcionEnTSobreAmortiguada(a1,a2,s1,s2,tiempoX);
                    series.appendData(new DataPoint(tiempoX,voltCorriente),true,500);
                }
                funcion.addSeries(series);
            }*/
        }catch (NumberFormatException e){
            Toast.makeText(this, "error al convertir valores", Toast.LENGTH_SHORT).show();
        }


    }
    public double funcionEnTSobreAmortiguada (double a1, double a2, double s1, double s2, double t){
        double funcionFinal =  a1*Math.pow(Math.E,s1*t) + a2*Math.pow(Math.E,s2*t);
        return funcionFinal;
    }
}