package com.gaffaryucel.flow.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gaffaryucel.flow.databinding.RowDateBinding
import com.gaffaryucel.flow.view.TrackFragment
import com.gaffaryucel.flow.view.TrackFragmentDirections

class TrackAdapter(val where : String) : RecyclerView.Adapter<TrackAdapter.ViewHolder>() {
    var trackList=  mutableListOf<String>()
    inner class ViewHolder(val binding : RowDateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowDateBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = trackList[position]
        holder.binding.text1.text = track
        holder.itemView.setOnClickListener {
            val action  = TrackFragmentDirections.actionTrackFragmentToEventsFragment(track)
            Navigation.findNavController(it).navigate(action)
        }
    }
    override fun getItemCount(): Int {
        return trackList.size
    }
}
