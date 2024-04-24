package com.example.circuitorlc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

lateinit var TipoCircuito: TextView

lateinit var Resistencia : EditText
lateinit var Inductor: EditText
lateinit var Capacitor: EditText
lateinit var Corriente: EditText
lateinit var Voltaje: EditText

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


    }


}