package com.gaffaryucel.flow.viewmodel

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Handler
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.gaffaryucel.flow.R
import com.gaffaryucel.flow.model.TrackModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class FlowViewModel(application: Application) : AndroidViewModel(application) {

    private val userid = FirebaseAuth.getInstance().currentUser!!.uid

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault())
    private var mycurrentTime = Calendar.getInstance().time
    private var formattedTime = ""

    private var startTime: Long = 0
    private var elapsedTime: Long = 0

    private var isRunning: Boolean = false

    private val handler: Handler = Handler()
    var currentTime : Long = 0

    private var notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(application)
    private val channelId = "stopwatch_channel"
    private val notificationId = 1

    var cronometre = MutableLiveData<String>()
    var time = MutableLiveData<String>()

    private val timerRunnable: Runnable = object : Runnable {
        override fun run() {
            currentTime = System.currentTimeMillis()
            elapsedTime = currentTime - startTime
            updateTimerText()
            handler.postDelayed(this, 10) // Update every 10 milliseconds
        }
    }

    private val databaseUrl = "https://akademik-fcaca-default-rtdb.europe-west1.firebasedatabase.app/"
    private val database = FirebaseDatabase.getInstance(databaseUrl)
    private val myRef = database.getReference("referance")
    private var start_time = ""

    init {
        // Initialize notification manager
        notificationManager = NotificationManagerCompat.from(application)

        // Create notification channel (for Android Oreo and above)
        createNotificationChannel()

        //

    }
    fun startTimer() {
        if (!isRunning) {
            start_time = getStartTime()
            startTime = System.currentTimeMillis() - elapsedTime
            handler.postDelayed(timerRunnable, 0)
            isRunning = true
            cronometre.value = "started"
            startStudying()
        }
    }
    fun stopTimer() {
        if (isRunning) {
            println(startTime)
            handler.removeCallbacks(timerRunnable)
            isRunning = false
            cronometre.value = "stopped"
            finishStudying()
        }
    }
    fun resetTimer() {
        stopTimer()
        elapsedTime = 0
        updateTimerText()
        cronometre.value = "reseted"
    }
    private fun updateTimerText() {
        val minutes = (elapsedTime / 60000).toInt()
        val seconds = (elapsedTime % 60000 / 1000).toInt()
        val milliseconds = (elapsedTime % 1000 / 10).toInt()

        val timeText = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
        time.value = timeText
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channelllll_name"
            val descriptionText = "getString(R.string.channel_description)"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
    fun updateNotification(time: String,context : Context) {
        val formattedTime = time.substring(0, 5) // Sadece ilk 5 karakteri kullanarak dakika ve saniye değerlerini alıyoruz
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Odaklan")
            .setContentText(formattedTime)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            .setSound(null)
            .setOnlyAlertOnce(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    fun trackUserSession(isEntered: Boolean) {
        mycurrentTime = Calendar.getInstance().time
        formattedTime = dateFormat.format(mycurrentTime)
        val tarih = formattedTime.substringBefore(" ")
        val saat = formattedTime.substringAfter(" ")
        if (isEntered) {
            if (isRunning){
                var track = TrackModel()
                track.time = saat
                track.status = "çakışmaya döndün"
                myRef.child(userid).child(tarih.replace("/"," "))
                    .child(start_time)
                    .child(saat)
                    .setValue(track)
                    .addOnCompleteListener {
                        println("complate")
                    }.addOnFailureListener{
                        println("fail")
                    }
            }
        } else {
            if (isRunning){
                val databaseUrl = "https://akademik-fcaca-default-rtdb.europe-west1.firebasedatabase.app/"
                val database = FirebaseDatabase.getInstance(databaseUrl)
                val myRef = database.getReference("referance")
                var track = TrackModel()
                track.time = saat
                track.status = "çıktın"
                myRef.child(userid)
                    .child(tarih.replace("/"," "))
                    .child(start_time)
                    .child(saat)
                    .setValue(track)
                    .addOnCompleteListener {

                    }.addOnFailureListener{

                    }
            }
        }
    }

    private fun startStudying(){
        mycurrentTime = Calendar.getInstance().time
        formattedTime = dateFormat.format(mycurrentTime)
        val tarih = formattedTime.substringBefore(" ")
        val saat = formattedTime.substringAfter(" ")
        val track = TrackModel()
        track.time = saat
        track.status = "Çalışmaya Başladı"
        myRef.child(userid)
            .child(tarih.replace("/"," "))
            .child(start_time)
            .child(saat)
            .setValue(track)
            .addOnCompleteListener {
                println("complate")
            }.addOnFailureListener{
                println("fail")
            }
    }

    private fun finishStudying(){
        mycurrentTime = Calendar.getInstance().time
        formattedTime = dateFormat.format(mycurrentTime)
        val tarih = formattedTime.substringBefore(" ")
        val saat = formattedTime.substringAfter(" ")
        val track = TrackModel()
        track.time = saat
        track.status = "Çalışma Bitti"
        myRef.child(userid)
            .child(tarih.replace("/"," "))
            .child(start_time)
            .child(saat)
            .setValue(track)
            .addOnCompleteListener {

            }.addOnFailureListener{

            }
    }
    private fun getStartTime() : String{
        mycurrentTime = Calendar.getInstance().time
        formattedTime = dateFormat.format(mycurrentTime)
        return formattedTime.substringAfter(" ")
    }
}