package com.example.memorygame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memorygame.model.Frutas

class AdaptadorFrutas(
    private val context: Context,
    private val dataset: List<Frutas>
    ) : RecyclerView.Adapter<AdaptadorFrutas.FrutaViewHolder>() {

    class FrutaViewHolder( private val view: View
    ) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrutaViewHolder {
        val adaptadorLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fruta, parent, false)
        return FrutaViewHolder(adaptadorLayout)
    }

    override fun onBindViewHolder(holder: FrutaViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = context.resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.imageResID)
    }

    override fun getItemCount() = dataset.size
}