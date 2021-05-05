package com.eniola.addressbookapp.repository

import android.content.Context
import androidx.room.*
import com.eniola.addressbookapp.BuildConfig

/*@Database(entities = [], version = 1, exportSchema = false)*/
abstract class AppRoomDatabase: RoomDatabase()  {
    companion object {
        private val DATABASE_NAME =
            if(BuildConfig.DEBUG) "address_book" else "address_book@@@.db"
        private var sInstance: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase? {
            if(sInstance == null){
               synchronized(AppRoomDatabase::class.java){
                   sInstance = Room.databaseBuilder(context,
                       AppRoomDatabase::class.java, DATABASE_NAME
                   )
                       .build()
               }
            }
            return sInstance
        }
    }

}