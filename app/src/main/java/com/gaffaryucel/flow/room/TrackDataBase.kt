package com.gaffaryucel.flow.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gaffaryucel.flow.model.TrackModel

abstract class TrackDataBase : RoomDatabase(){
    abstract fun trackDao() : TrackDao
}