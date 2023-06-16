package com.gaffaryucel.flow.service

import android.content.Context
import android.media.MediaPlayer
import com.gaffaryucel.flow.R


class MusicPlayer(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun startMusic(selected : String) {
        when(selected){
            "music1"->{
                mediaPlayer = MediaPlayer.create(context, R.raw.music1)
                mediaPlayer?.start()
            }
            "music2"->{
                mediaPlayer = MediaPlayer.create(context, R.raw.music2)
                mediaPlayer?.start()
            }
            "music3"->{
                mediaPlayer = MediaPlayer.create(context, R.raw.music3)
                mediaPlayer?.start()
            }
            "music4"->{
                mediaPlayer = MediaPlayer.create(context, R.raw.music4)
                mediaPlayer?.start()
            }
            else->{
                mediaPlayer = MediaPlayer.create(context, R.raw.music1)
                mediaPlayer?.start()
            }
        }
    }

    fun stopMusic() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}