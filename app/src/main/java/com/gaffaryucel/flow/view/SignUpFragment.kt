package com.gaffaryucel.flow.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.gaffaryucel.flow.R
import com.gaffaryucel.flow.databinding.FragmentFlowBinding
import com.gaffaryucel.flow.databinding.FragmentSignUpBinding
import com.gaffaryucel.flow.service.MusicPlayer
import com.gaffaryucel.flow.viewmodel.SignInViewModel
import com.gaffaryucel.flow.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SignUpFragment : Fragment() {
    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        binding.signUpButton.setOnClickListener{
            val mail = binding.emailedittext.text.toString()
            val password = binding.passwordEdittext.text.toString()
           viewModel.firebaseKayit(mail,password)
        }
        observeLiveData()
    }
    private fun observeLiveData() {
        val kayitBasariliObserver = Observer<FirebaseUser?> { kullanici ->
            if (kullanici != null) {
                val action = SignInFragmentDirections.actionSignInFragmentToFlowFragment2()
                Navigation.findNavController(requireView()).navigate(action)
            }
        }
        viewModel.kayitBasarili.observe(viewLifecycleOwner, kayitBasariliObserver)
    }
}