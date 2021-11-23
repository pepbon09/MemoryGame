package com.example.memorygame

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.graphics.toColor
import androidx.gridlayout.widget.GridLayout
import com.example.memorygame.databinding.ActivityMainBinding
import kotlin.collections.ArrayList
import kotlin.random.Random

class Partida : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tablero: GridLayout
    private lateinit var mp: MediaPlayer

    private val cartasTablero = arrayListOf<String>(
        "Naranja", "Naranja", "Manzana", "Manzana", "Pera", "Pera",
        "Limon", "Limon", "Platano", "Platano", "Cerezas", "Cerezas",
        "Uvas", "Uvas", "Piña", "Piña", "Sandia", "Sandia",
        "Melon", "Melon", "Kiwi", "Kiwi", "Mango", "Mango",
        "Fresas", "Fresas", "Arandanos", "Arandanos", "Aguacate", "Aguacate",
        "Melocoton", "Melocoton", "Granada", "Granada", "Frambuesa", "Frambuesa"
    )

    private var bloqueado = false
    private var parejas = 0
    private var primeraCarta: ImageView? = null
    private var segundaCarta: ImageView? = null
    private var jugadorActual = 1
    private var puntosJ1 = 0
    private var puntosJ2 = 0
    private var fondoCarta: Int = 0
    private var frutaFavorita: String = "Ninguna"
    private val handler: Handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tablero = binding.tablero
        var dimensiones: String = intent.getStringExtra("dimensiones") as String
        frutaFavorita = intent.getStringExtra("fruta_fav") as String
        mp = MediaPlayer.create(this, R.raw.partida)
        mp.start()
        when (frutaFavorita) {
            "Naranja" -> binding.root.background = getDrawable(R.drawable.orange_bg)
            "Manzana" -> binding.root.background = getDrawable(R.drawable.apple_bg)
            "Piña" -> binding.root.background = getDrawable(R.drawable.pineapple_bg)
        }
        generarTablero(dimensiones)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.game_options,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.title == "Reiniciar") {
            var i: Intent = this.intent
            finish()
            startActivity(i)
        } else if (item.title == "Volver") {
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        mp.pause()
    }

    override fun onRestart() {
        super.onRestart()
        mp.start()
    }

    fun generarTablero(dimensiones: String) {
        var nCartas = 0
        var auxCartasTablero = ArrayList<String>()
        var atributosExtra = ArrayList<Int>()
        when (dimensiones) {
            "4x3" -> {
                nCartas = 11
                auxCartasTablero = ArrayList(cartasTablero.subList(0,12))
                parejas = 6
                tablero.columnCount = 4
                tablero.rowCount = 3
                fondoCarta = R.drawable.tablero_easy
                atributosExtra.add(350)//height
                atributosExtra.add(35)//margin left
                atributosExtra.add(0)//margin top
                atributosExtra.add(35)//margin right
                atributosExtra.add(0)//margin bottom
            }
            "5x4" -> {
                nCartas = 19
                auxCartasTablero = ArrayList(cartasTablero.subList(0,20))
                parejas = 10
                tablero.columnCount = 5
                tablero.rowCount = 4
                fondoCarta = R.drawable.tablero_medium
                atributosExtra.add(275)//height
                atributosExtra.add(20)//margin left
                atributosExtra.add(0)//margin top
                atributosExtra.add(20)//margin right
                atributosExtra.add(0)//margin bottom
            }
            "6x6" -> {
                nCartas = 35
                auxCartasTablero = cartasTablero
                parejas = 18
                tablero.columnCount = 6
                tablero.rowCount = 6
                fondoCarta = R.drawable.tablero_hard
                atributosExtra.add(170)//height
                atributosExtra.add(5)//margin left
                atributosExtra.add(10)//margin top
                atributosExtra.add(5)//margin right
                atributosExtra.add(10)//margin bottom
            }
        }

        for (i in 0..nCartas) {
            var randInt = Random.nextInt(auxCartasTablero.size)
            var valCarta = auxCartasTablero.removeAt(randInt)

            var carta = ImageView(this)
            var gridParams: GridLayout.LayoutParams = GridLayout.LayoutParams (
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
            )
            gridParams.width = 0
            gridParams.height = atributosExtra[0]
            gridParams.setMargins(atributosExtra[1],atributosExtra[2],atributosExtra[3],atributosExtra[4])
            carta.layoutParams = gridParams
            carta.setImageResource(fondoCarta)
            carta.setOnClickListener { destaparCarta(carta) }
            carta.setTag(R.string.fruta, valCarta)
            carta.setTag(R.string.destapada, false)
            tablero.addView(carta)
        }
    }

    fun destaparCarta(carta: ImageView) {
        if (carta.getTag(R.string.destapada) == false && !bloqueado) {
            var imgFruta = when (carta.getTag(R.string.fruta) as String) {
                "Naranja" -> R.drawable.naranja
                "Manzana" -> R.drawable.manzana
                "Pera" -> R.drawable.pera
                "Limon" -> R.drawable.limon
                "Platano" -> R.drawable.platano
                "Cerezas" -> R.drawable.cerezas
                "Uvas" -> R.drawable.uvas
                "Piña" -> R.drawable.pina
                "Sandia" -> R.drawable.sandia
                "Melon" -> R.drawable.melon
                "Kiwi" -> R.drawable.kiwi
                "Mango" -> R.drawable.mango
                "Fresas" -> R.drawable.fresas
                "Arandanos" -> R.drawable.arandanos
                "Aguacate" -> R.drawable.aguacate
                "Melocoton" -> R.drawable.melocoton
                "Granada" -> R.drawable.granada
                "Frambuesa" -> R.drawable.frambuesa
                else -> fondoCarta
            }
            carta.setImageResource(imgFruta)
            carta.setTag(R.string.destapada, true)
            binding.frutaSeleccionada.text = carta.getTag(R.string.fruta) as String
            if (primeraCarta == null) {
                primeraCarta = carta
            } else {
                segundaCarta = carta
                comprobarPareja()
            }
        }
    }

    fun comprobarPareja() {
        bloqueado = true
        val fruta1 = primeraCarta?.getTag(R.string.fruta) as String
        val fruta2 = segundaCarta?.getTag(R.string.fruta) as String
        if (fruta1 == fruta2) {
            var punto = 0
            if (fruta1 == frutaFavorita) {
                punto = 100
                binding.txtFrutaFav.text = "Fruta favorita! x2"
            } else {
                punto = 50
            }
            binding.txtMensaje.text = "Punto para J$jugadorActual!"
            if (jugadorActual == 1) {
                puntosJ1 += punto
                binding.puntuacionJ1.text = puntosJ1.toString()
            } else {
                puntosJ2 += punto
                binding.puntuacionJ2.text = puntosJ2.toString()
            }
            parejas -= 1
            if (parejas <= 0) {
                handler.postDelayed({ finalizarPartida() }, 1000)
            } else {
                handler.postDelayed({ ocultarCartas() }, 1000)
            }
        } else {
            binding.txtMensaje.text = "Falla J$jugadorActual y pierde el turno"
            if (jugadorActual == 1) {
                jugadorActual = 2
            } else {
                jugadorActual = 1
            }
            handler.postDelayed({
                primeraCarta!!.setImageResource(fondoCarta)
                primeraCarta!!.setTag(R.string.destapada, false)
                segundaCarta!!.setImageResource(fondoCarta)
                segundaCarta!!.setTag(R.string.destapada, false)
                ocultarCartas() },1000)
        }
    }

    fun ocultarCartas() {
        primeraCarta = null
        segundaCarta = null
        bloqueado = false
        binding.txtMensaje.text = "Turno del J$jugadorActual"
        binding.txtMensaje.setTextColor(resources.getColor(if (jugadorActual == 1) R.color.blue_primary else R.color.red_primary))
        binding.txtFrutaFav.text = ""
    }

    fun finalizarPartida() {
        binding.txtFrutaFav.text = ""
        binding.frutaSeleccionada.text = ""
        if (puntosJ1 > puntosJ2) {
            binding.txtMensaje.text = "Ha ganado J1"
            binding.txtMensaje.setTextColor(resources.getColor(R.color.blue_primary))
        } else if (puntosJ2 > puntosJ1) {
            binding.txtMensaje.text = "Ha ganado J2"
            binding.txtMensaje.setTextColor(resources.getColor(R.color.red_primary))
        } else {
            binding.txtMensaje.text = "Empate"
            binding.txtMensaje.setTextColor(resources.getColor(R.color.black))
        }
    }
}