package com.gaffaryucel.flow.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gaffaryucel.flow.adapter.TrackAdapter
import com.gaffaryucel.flow.databinding.FragmentTrackBinding
import com.gaffaryucel.flow.viewmodel.TrackViewModel
import com.google.firebase.auth.FirebaseAuth

class TrackFragment : Fragment() {

    companion object {
        fun newInstance() = TrackFragment()
    }

    private lateinit var viewModel: TrackViewModel
    private lateinit var trackAdapter: TrackAdapter
    private lateinit var binding: FragmentTrackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrackViewModel::class.java)

        trackAdapter = TrackAdapter("date")
        // TODO: Use the ViewModel
        val spanCount = 2 // Her satırda 2 eleman
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), spanCount, RecyclerView.VERTICAL, false)
        binding.trackrecyclerView.layoutManager = layoutManager

        observeLiveData()

    }
    private fun observeLiveData(){
        viewModel.dates.observe(viewLifecycleOwner, Observer {
            trackAdapter.trackList = it as ArrayList<String>
            binding.trackrecyclerView.adapter = trackAdapter
            trackAdapter.notifyDataSetChanged()
        })
    }
}