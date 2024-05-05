package com.example.circuitorlc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.util.List;

import Calculo.RLCParalelo;

public class Graficar extends AppCompatActivity {

    private LineGraphSeries<DataPoint> funGrafica;
    private GraphView graph;
    private TextView lblTipoGrafica;
    private  TextView lblInformacion;
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
        lblTipoGrafica = findViewById(R.id.lblTipoGrafica);
        lblInformacion = findViewById(R.id.lblDatos);
        DecimalFormat df = new DecimalFormat("#.00"); //funcion para formatear con 2 decimales los numeros double

        //Tomando los datos capturados del activity anterior
        Intent intent = getIntent();
        double r =Double.parseDouble( intent.getStringExtra("R"));
        double l =Double.parseDouble( intent.getStringExtra("L"));
        double c =Double.parseDouble( intent.getStringExtra("C"));
        double v0 =Double.parseDouble( intent.getStringExtra("v0"));
        double i0 =Double.parseDouble( intent.getStringExtra("i0"));
        int seleccion = intent.getIntExtra("rb",-1);

        RLCParalelo paralelo = new RLCParalelo();
        //calcular alpha y omega para saber el tipo de grafica
        double alpha = paralelo.calcAlpha(r,c);
        double omega = paralelo.calcOmega(l,c);

        //declar la serie de datos para llenar la grafica
        funGrafica = new LineGraphSeries<DataPoint>();
        String funcionResultante="";//como quiero que la funcion resultante el texto varie segun la funcion requerida
        String complemento ="";
        String tipoFuncion ="";
        if(alpha > omega){
            lblTipoGrafica.setText("Circuito RLC Paralelo sobreamortiguado");
            List<Double> arrayConstantesCorriente;
            if (seleccion == 0){
                arrayConstantesCorriente =  paralelo.ConstantesVoltSobreAmortiguado(r,l,c,i0,v0);//para calcular el voltaje en el inductor
                complemento = "v(t)";
                tipoFuncion ="Funcion v(t) en el capacitor";
            }
            else{
                arrayConstantesCorriente =  paralelo.ConstantesCorrienteSobreAmortiguado(r,l,c,i0,v0); //para calcular la corriente en el inductor
                complemento = "i(t)";
                tipoFuncion ="Funcion i(t) en el inductor";
            }
            //como en el if elijo la funcion con la cual obtener las constantes de mi funcion aca las asigno
            assert arrayConstantesCorriente != null;
            double a1 = arrayConstantesCorriente.get(0);
            double a2 = arrayConstantesCorriente.get(1);
            double s1 = arrayConstantesCorriente.get(2);
            double s2 = arrayConstantesCorriente.get(3);
            //una vez obtengo las constantes formo la funcion para luego mostrarla en el label
            funcionResultante= String.format(complemento+"=%s*e^(%s*t)+%s*e^(%s*t)",df.format(a1),df.format(s1),
                    df.format(a2),df.format(s2));
            //debo mostrar la funcion hasta un tiempo (en segundos) considerable para que ya se haya estabilizado su curva
            for (double t = -1; t <= 5; t += 0.001) {
                double y = paralelo.funcionTSobreAmortiguada(a1,a2,s1,s2,t); // Calcula la función f(x) que desees
                funGrafica.appendData(new DataPoint(t, y), true, 5000);
                //fungrafica es una "coleccion" de puntos calculados, el cual por cada iteracion del for se agrega
            }
            lblInformacion.setText("Alpha: "+ String.format("%.2f",alpha) + " Omega: "+ String.format("%.2f", omega)
            +"\n "+tipoFuncion+"\n"+funcionResultante );
        } else if (alpha==omega) {
            lblTipoGrafica.setText("Circuito RLC Paralelo criticamenteamortiguado");
        } else if (alpha<omega) {
            lblTipoGrafica.setText("Circuito RLC Paralelo subamortiguado");
        }


        graph = findViewById(R.id.grafica);
        //graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-1);
        graph.getViewport().setMaxX(5);
        /*graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY();
        graph.getViewport().setMaxY();*/
        graph.getViewport().setScalable(true); // Permite hacer zoom
        //graph.getViewport().setScrollable(true);
        //graph.getLegendRenderer().setVisible(true); //para poner la leyenda de cada grafica
        //graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph.getGridLabelRenderer().setHorizontalAxisTitle("Tiempo (s)");
        graph.getGridLabelRenderer().setVerticalAxisTitle(complemento);
        graph.addSeries(funGrafica);
    }


    public double f(double x){
        return  Math.pow(x,3);
    }

}