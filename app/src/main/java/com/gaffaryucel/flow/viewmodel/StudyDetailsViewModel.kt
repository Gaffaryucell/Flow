package com.gaffaryucel.flow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gaffaryucel.flow.model.TrackModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class StudyDetailsViewModel : ViewModel() {
    private val databaseUrl = "https://akademik-fcaca-default-rtdb.europe-west1.firebasedatabase.app/"
    private val database = FirebaseDatabase.getInstance(databaseUrl)
    private val myRef = database.getReference("referance")
    val details = MutableLiveData<List<TrackModel>>()
    private val userid = FirebaseAuth.getInstance().currentUser!!.uid

    fun getStudyInfo(date : String,hour : String){
        myRef.child(userid)
            .child(date)
            .child(hour)
            .orderByValue()
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dateList = ArrayList<TrackModel>()
                    for (snap in snapshot.children) {
                        val track = snap.getValue(TrackModel::class.java)
                        if (track != null){
                            dateList.add(track)
                        }
                    }
                    details.value = dateList
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}