package com.gaffaryucel.flow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    val kayitBasarili = MutableLiveData<FirebaseUser?>()
    val kayitHata = MutableLiveData<String>()

    fun firebaseKayit(email: String, sifre: String) {
        auth.createUserWithEmailAndPassword(email, sifre)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val kullanici = auth.currentUser
                    kayitBasarili.value = kullanici
                } else {
                    kayitHata.value = task.exception?.message
                }
            }
    }
}