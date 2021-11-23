package com.example.memorygame

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import com.example.memorygame.databinding.ActivityMenuPrincipalBinding

class MenuPrincipal : AppCompatActivity(), OnFragmentActionsListener {
    private lateinit var binding: ActivityMenuPrincipalBinding
    private lateinit var listaTableros : ArrayList<Tablero>
    private lateinit var mp: MediaPlayer
    private var frutaFavorita : String = "Ninguna"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mp = MediaPlayer.create(this, R.raw.menu)
        mp.start()

        val tableroFacil = Tablero(
            "Facil",
            "Perfecto para partidas rapidas",
            "4x3",
            R.drawable.tablero_easy
        )

        val tableroMedio = Tablero(
            "Medio",
            "El modo clasico",
            "5x4",
            R.drawable.tablero_medium
        )

        val tableroDificil = Tablero(
            "Dificil",
            "Bastante amplio",
            "6x6",
            R.drawable.tablero_hard
        )

        listaTableros = ArrayList()
        listaTableros.add(tableroFacil)
        listaTableros.add(tableroMedio)
        listaTableros.add(tableroDificil)

        binding.lwLista.isClickable = true
        binding.lwLista.adapter = AdaptadorTablero(this,listaTableros)

        binding.lwLista.setOnItemClickListener {
            parent, view, position, id ->

            val i = Intent(this, Partida::class.java)
            i.putExtra("dificultad", listaTableros[position].dificultad)
            i.putExtra("descripcion", listaTableros[position].descripcion)
            i.putExtra("dimensiones", listaTableros[position].dimensiones)
            i.putExtra("fruta_fav", frutaFavorita)

            startActivity(i)
            finish()
        }

    }

    override fun onStop() {
        super.onStop()
        mp.pause()
    }

    override fun onRestart() {
        super.onRestart()
        mp.start()
    }

    fun cargarGaleria(view: View) {
        val intent = Intent(this, Galeria::class.java)
        startActivity(intent)
    }

    override fun onClickFragmentButton() {
        val btnFruta = findViewById<Button>(R.id.btnFrutaFav)
        val menupopup = PopupMenu(this, btnFruta)
        menupopup.inflate(R.menu.fruta_fav)
        menupopup.setOnMenuItemClickListener {
            when (it.title) {
                "Naranja" -> {
                    binding.root.background = getDrawable(R.drawable.orange_bg)
                    frutaFavorita = "Naranja"
                }
                "Manzana" -> {
                    binding.root.background = getDrawable(R.drawable.apple_bg)
                    frutaFavorita = "Manzana"
                }
                "Piña" -> {
                    binding.root.background = getDrawable(R.drawable.pineapple_bg)
                    frutaFavorita = "Piña"
                }
            }
            true
        }
        menupopup.show()
    }
}