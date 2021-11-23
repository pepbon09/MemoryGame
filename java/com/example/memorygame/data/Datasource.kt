package com.example.memorygame.data

import com.example.memorygame.R
import com.example.memorygame.model.Frutas

class Datasource {
    fun loadFrutas(): List<Frutas> {
        return listOf<Frutas> (
            Frutas(R.string.fruta1, R.drawable.naranja),
            Frutas(R.string.fruta2, R.drawable.manzana),
            Frutas(R.string.fruta3, R.drawable.pera),
            Frutas(R.string.fruta4, R.drawable.limon),
            Frutas(R.string.fruta5, R.drawable.platano),
            Frutas(R.string.fruta6, R.drawable.cerezas),
            Frutas(R.string.fruta7, R.drawable.uvas),
            Frutas(R.string.fruta8, R.drawable.pina),
            Frutas(R.string.fruta9, R.drawable.sandia),
            Frutas(R.string.fruta10, R.drawable.melon),
            Frutas(R.string.fruta11, R.drawable.kiwi),
            Frutas(R.string.fruta12, R.drawable.mango),
            Frutas(R.string.fruta13, R.drawable.fresas),
            Frutas(R.string.fruta14, R.drawable.arandanos),
            Frutas(R.string.fruta15, R.drawable.aguacate),
            Frutas(R.string.fruta16, R.drawable.melocoton),
            Frutas(R.string.fruta17, R.drawable.granada),
            Frutas(R.string.fruta18, R.drawable.frambuesa),
        )
    }
}