package com.app.snaplearnai.shared.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(
//    entities = [
//
//    ],
//    version = 1,
//    exportSchema = true
//)
//abstract class AppDataBase : RoomDatabase() {
//
//    companion object {
//        @Volatile
//        private var instance: AppDataBase? = null
//
//        fun getInstance(context: Context): AppDataBase {
//            return instance ?: synchronized(this) {
//                Room.databaseBuilder(context, AppDataBase::class.java, "snap_learn_ai_db")
//                    .build()
//                    .also { instance = it }
//            }
//        }
//    }
//}