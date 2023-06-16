package com.gaffaryucel.flow.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaffaryucel.flow.R
import com.gaffaryucel.flow.adapter.EventAdapter
import com.gaffaryucel.flow.adapter.StudyDetailsAdapter
import com.gaffaryucel.flow.databinding.FragmentEventsBinding
import com.gaffaryucel.flow.databinding.FragmentStudyDetailsBinding
import com.gaffaryucel.flow.model.TrackModel
import com.gaffaryucel.flow.viewmodel.EventsViewModel
import com.gaffaryucel.flow.viewmodel.StudyDetailsViewModel

class StudyDetailsFragment : Fragment() {

    private lateinit var viewModel: StudyDetailsViewModel
    private lateinit var binding: FragmentStudyDetailsBinding
    private var date: String = ""
    private var hour: String = ""
    private lateinit var eventAdapter: StudyDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudyDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(StudyDetailsViewModel::class.java)
        // TODO: Use the ViewModel
        arguments?.let {
            date = it.getString("date") .toString()
            hour = it.getString("hour") .toString()
            eventAdapter = StudyDetailsAdapter()
            viewModel.getStudyInfo(date,hour)
        }
        binding.recyclerviewstudy.layoutManager = LinearLayoutManager(requireContext())
        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.details.observe(viewLifecycleOwner, Observer {
            eventAdapter.trackList = it as ArrayList<TrackModel>
            binding.recyclerviewstudy.adapter = eventAdapter
            eventAdapter.notifyDataSetChanged()
        })
    }
}