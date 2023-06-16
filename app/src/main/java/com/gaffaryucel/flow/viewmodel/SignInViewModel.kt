package com.gaffaryucel.flow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    val girisBasarili = MutableLiveData<FirebaseUser?>()
    val girisHata = MutableLiveData<String>()

    fun firebaseGiris(email: String, sifre: String) {
        println("giris")
        auth.signInWithEmailAndPassword(email, sifre)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val kullanici = auth.currentUser
                    girisBasarili.value = kullanici
                    println("success")
                } else {
                    girisHata.value = task.exception?.message
                }
            }
    }
}