package com.example.memorygame

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.memorygame.data.Datasource

class Galeria : AppCompatActivity() {
    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galeria)
        val myDataset = Datasource().loadFrutas()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = AdaptadorFrutas(this, myDataset)
        recyclerView.setHasFixedSize(true)
        mp = MediaPlayer.create(this, R.raw.galeria)
        mp.start()
    }

    override fun onStop() {
        super.onStop()
        mp.pause()
    }

    override fun onRestart() {
        super.onRestart()
        mp.start()
    }
}