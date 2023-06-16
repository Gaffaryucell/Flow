package com.gaffaryucel.flow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TrackViewModel : ViewModel() {
    private val databaseUrl = "https://akademik-fcaca-default-rtdb.europe-west1.firebasedatabase.app/"
    private val database = FirebaseDatabase.getInstance(databaseUrl)
    private val myRef = database.getReference("referance")
    val dates = MutableLiveData<List<String>>()
    private val userid = FirebaseAuth.getInstance().currentUser!!.uid

    init {
        getFlowInfo()
    }

    fun getFlowInfo(){
        myRef.child(userid).orderByPriority()
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dateList = ArrayList<String>()
                    for (snap in snapshot.children){
                        val key = snap.key.toString().replace(" ","/")
                        println(key)
                        dateList.add(key)
                    }
                    dates.value = dateList
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}