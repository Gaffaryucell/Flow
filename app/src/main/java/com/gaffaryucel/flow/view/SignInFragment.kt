package com.gaffaryucel.flow.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.gaffaryucel.flow.R
import com.gaffaryucel.flow.databinding.FragmentFlowBinding
import com.gaffaryucel.flow.databinding.FragmentSignInBinding
import com.gaffaryucel.flow.service.MusicPlayer
import com.gaffaryucel.flow.viewmodel.FlowViewModel
import com.gaffaryucel.flow.viewmodel.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInFragment : Fragment() {

    private lateinit var viewModel: SignInViewModel
    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser != null){
            val action = SignInFragmentDirections.actionSignInFragmentToFlowFragment2()
            Navigation.findNavController(requireView()).navigate(action)
        }
        binding.signInButton.setOnClickListener{ myview ->
            val email = binding.emailedittext.text.toString()
            val password = binding.passwordEdittext.text.toString()
            viewModel.firebaseGiris(email,password)
        }
        binding.signUp.setOnClickListener{
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment2()
            Navigation.findNavController(it).navigate(action)
        }
        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.girisBasarili.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val action = SignInFragmentDirections.actionSignInFragmentToFlowFragment2()
                Navigation.findNavController(requireView()).navigate(action)
            }
        })
    }
}