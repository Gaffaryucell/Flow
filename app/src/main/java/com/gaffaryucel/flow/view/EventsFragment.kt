package com.gaffaryucel.flow.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gaffaryucel.flow.R
import com.gaffaryucel.flow.adapter.EventAdapter
import com.gaffaryucel.flow.adapter.TrackAdapter
import com.gaffaryucel.flow.databinding.FragmentEventsBinding
import com.gaffaryucel.flow.databinding.FragmentTrackBinding
import com.gaffaryucel.flow.viewmodel.EventsViewModel

class EventsFragment : Fragment() {

    companion object {
        fun newInstance() = EventsFragment()
    }

    private lateinit var viewModel: EventsViewModel
    private lateinit var binding: FragmentEventsBinding
    private var date: String = ""
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EventsViewModel::class.java)
        arguments?.let {
            date = it.getString("date") .toString()
            val changedDate = date.replace("/"," ")
            eventAdapter = EventAdapter(changedDate)
            viewModel.getDateFromDate(changedDate)
        }
        // TODO: Use the ViewModel
        val spanCount = 3 // Her satırda 2 eleman
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), spanCount, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager

        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.dates.observe(viewLifecycleOwner, Observer {
            eventAdapter.trackList = it as ArrayList<String>
            binding.recyclerView.adapter = eventAdapter
            eventAdapter.notifyDataSetChanged()
        })
    }
}