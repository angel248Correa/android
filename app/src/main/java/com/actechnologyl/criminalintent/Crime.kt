package com.actechnologyl.criminalintent

import androidx.room.PrimaryKey
import java.util.*

@androidx.room.Entity
data class Crime(@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var date: Date = Date(),
                 var isSolved: Boolean = false,
                 var suspect: String = " ",
                  ) {

    fun isBlank(): Boolean {
        return false
    }

    val photoFileName
    get() = "IMG_id.jpg"
                  }



