package com.example.memorygame

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class AdaptadorTablero (private val context: Activity, private val
listaTablero:ArrayList<Tablero>): ArrayAdapter<Tablero>(context, R.layout.item_tablero, listaTablero) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.item_tablero, null)

        val txtDificultad: TextView = view.findViewById(R.id.txtDificultad)
        val txtDimensiones: TextView = view.findViewById(R.id.txtDimensiones)
        val txtDescripcion: TextView = view.findViewById(R.id.txtDescripcion)
        val imgTablero: ImageView = view.findViewById(R.id.imgTablero)

        val tablero = listaTablero[position]

        txtDificultad.text = tablero.dificultad
        txtDimensiones.text = tablero.dimensiones
        txtDescripcion.text = tablero.descripcion
        imgTablero.setImageResource(tablero.imagenEjemplo)

        return view
    }

}