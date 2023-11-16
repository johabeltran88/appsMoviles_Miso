package com.example.test

import android.app.Application
import com.example.test.database.VinylRoomDatabase

class VinylsApplication: Application()  {
    val database by lazy { VinylRoomDatabase.getDatabase(this) }
}