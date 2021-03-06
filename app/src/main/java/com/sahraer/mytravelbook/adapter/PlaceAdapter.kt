package com.sahraer.mytravelbook.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.Placeholder
import androidx.recyclerview.widget.RecyclerView
import com.sahraer.mytravelbook.databinding.RecylerRowBinding
import com.sahraer.mytravelbook.model.Place
import com.sahraer.mytravelbook.view.MapsActivity

class PlaceAdapter(val placeList : List<Place>) : RecyclerView.Adapter<PlaceAdapter.PlaceHolder>(){
    class PlaceHolder(val recylerRowBinding: RecylerRowBinding) : RecyclerView.ViewHolder(recylerRowBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val recylerRowBinding = RecylerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlaceHolder(recylerRowBinding)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.recylerRowBinding.recylerViewText.text = placeList.get(position).name
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,MapsActivity::class.java)
            intent.putExtra("selectedPlace",placeList.get(position))
            intent.putExtra("info","old")
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return placeList.size
    }
}