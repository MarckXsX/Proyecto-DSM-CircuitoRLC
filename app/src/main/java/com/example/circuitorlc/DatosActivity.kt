package com.example.circuitorlc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

lateinit var TipoCircuito: TextView

lateinit var Resistencia : EditText
lateinit var Inductor: EditText
lateinit var Capacitor: EditText
lateinit var Corriente: EditText
lateinit var Voltaje: EditText
lateinit var BotonCalcular: Button
lateinit var TxtTiempo : EditText
lateinit var Foto: ImageView

class DatosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos)
        val datoRecibido = intent.getStringExtra("TipoCircuito")
        if(datoRecibido == "Serie"){
            TipoCircuito = findViewById(R.id.LblCircuito)
            TipoCircuito.setText(datoRecibido)
            Foto = findViewById(R.id.FotoCircuito)
            Foto.setImageResource(R.drawable.serie)

        }
        else if(datoRecibido == "Paralelo"){
            TipoCircuito = findViewById(R.id.LblCircuito)
            TipoCircuito.setText(datoRecibido)
            Foto = findViewById(R.id.FotoCircuito)
            Foto.setImageResource(R.drawable.paralelo)

        }

        Resistencia = findViewById(R.id.TxtResistencia)
        Inductor = findViewById(R.id.TxtInductor)
        Capacitor = findViewById(R.id.TxtCapacitor)
        Corriente = findViewById(R.id.TxtCorriente)
        Voltaje = findViewById(R.id.TxtVoltaje)
        TxtTiempo = findViewById(R.id.txtTiempoFinal)



        val intentGraf = Intent(this,Graficar::class.java)
        BotonCalcular = findViewById(R.id.btnGraficar)
        BotonCalcular.setOnClickListener {
            val r = Resistencia.text.toString()
            val inductancia = Inductor.text.toString()
            val capacitancia = Capacitor.text.toString()
            val voltI = Voltaje.text.toString()
            val corrienteI = Corriente.text.toString()
            val tiempo = TxtTiempo.text.toString()
            intentGraf.putExtra("R",r)
            intentGraf.putExtra("L", inductancia)
            intentGraf.putExtra("C", capacitancia)
            intentGraf.putExtra("v0",voltI)
            intentGraf.putExtra("i0",corrienteI)
            intentGraf.putExtra("tf",tiempo)
            startActivity(intentGraf)
        }
    }

}