package com.gaffaryucel.flow.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gaffaryucel.flow.R
import com.gaffaryucel.flow.databinding.FragmentFlowBinding
import com.gaffaryucel.flow.service.MusicPlayer
import com.gaffaryucel.flow.viewmodel.FlowViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class FlowFragment : Fragment() {


    private lateinit var viewModel: FlowViewModel
    private lateinit var binding: FragmentFlowBinding
    private lateinit var musicPlayer: MusicPlayer
    private var selected = "music1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlowBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(FlowViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicPlayer = MusicPlayer(requireContext())

        binding.startButton.setOnClickListener {
            viewModel.startTimer()
            musicPlayer.startMusic(selected)
        }
        binding.stopButton.setOnClickListener {
            viewModel.stopTimer()
            musicPlayer.stopMusic()
        }
        binding.resetButton.setOnClickListener {
            viewModel.resetTimer()
            musicPlayer.stopMusic()
        }
        binding.musicImageView.setOnClickListener {
           showpopup()
        }
        binding.trackimageView.setOnClickListener {
            val action = FlowFragmentDirections.actionFlowFragmentToTrackFragment()
            Navigation.findNavController(it).navigate(action)
        }
        binding.cronometre.setOnClickListener {

        }
        binding.timer.setOnClickListener {
            
        }
        observeLiveData()
    }
    private fun observeLiveData() {
        viewModel.time.observe(viewLifecycleOwner, Observer {
            binding.apply {
                time = it
            }
            viewModel.updateNotification(it,requireContext())
        })
        viewModel.cronometre.observe(viewLifecycleOwner, Observer {
            when (it.toString()) {
                "started" -> {
                    binding.startButton.isEnabled = false
                    binding.stopButton.isEnabled = true
                    binding.resetButton.isEnabled = true
                }
                "stopped" -> {
                    binding.startButton.isEnabled = true
                    binding.stopButton.isEnabled = false
                    binding.resetButton.isEnabled = true
                }
                "reseted" -> {
                    binding.startButton.isEnabled = true
                    binding.stopButton.isEnabled = false
                    binding.resetButton.isEnabled = false
                }
                else -> {
                    binding.startButton.isEnabled = true
                    binding.stopButton.isEnabled = true
                    binding.resetButton.isEnabled = true
                }
            }
        })
    }

    private fun showpopup(){
        //inlater kısmı
        val inflater = LayoutInflater.from(requireContext())
        val popupView = inflater.inflate(R.layout.popup_layout, null)
        val popupWindow = PopupWindow(
            popupView,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.popup_animation)
        popupWindow.animationStyle = android.R.style.Animation_Dialog
        popupWindow.contentView.startAnimation(animation)

        popupWindow.showAtLocation(
            requireActivity().findViewById(R.id.fragmentContainerView),
            Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
            0,
            0
        )
        val option1 = popupView.findViewById<TextView>(R.id.option1)
        val option2 = popupView.findViewById<TextView>(R.id.option2)
        val option3 = popupView.findViewById<TextView>(R.id.option3)
        val option4 = popupView.findViewById<TextView>(R.id.option4)
        option1.setOnClickListener {
            selected = "music1"
            popupWindow.dismiss()
        }
        option2.setOnClickListener {
            selected = "music2"
            popupWindow.dismiss()
        }
        option3.setOnClickListener {
            selected = "music3"
            popupWindow.dismiss()
        }
        option4.setOnClickListener {
            selected = "music4"
            popupWindow.dismiss()
        }
    }
    override fun onStop() {
        super.onStop()
        viewModel.trackUserSession(false)
    }
    override fun onResume() {
        super.onResume()
        viewModel.trackUserSession(true)
    }
}

