package com.gaffaryucel.flow.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class TrackModel{
    var time : String? =null
    var status : String? =null
    constructor()
    constructor(time : String,status : String){
        this.time = time
        this.status = status
    }
}

