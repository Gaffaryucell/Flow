package com.gaffaryucel.flow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gaffaryucel.flow.databinding.RowDateBinding
import com.gaffaryucel.flow.databinding.RowStudyDetailsBinding
import com.gaffaryucel.flow.model.TrackModel
import com.gaffaryucel.flow.view.EventsFragmentDirections

class StudyDetailsAdapter () : RecyclerView.Adapter<StudyDetailsAdapter.ViewHolder>() {
    var trackList=  mutableListOf<TrackModel>()
    inner class ViewHolder(val binding : RowStudyDetailsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowStudyDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = trackList[position]
        holder.binding.statusText.text = track.status
        holder.binding.timeTextView.text = track.time
    }
    override fun getItemCount(): Int {
        return trackList.size
    }
}