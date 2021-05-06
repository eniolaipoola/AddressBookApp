package com.eniola.addressbookapp.di

import android.content.Context
import com.eniola.addressbookapp.repository.AppRoomDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class ApplicationModule  {

    @Singleton
    @Provides
    fun provideIODispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): AppRoomDatabase {
        return AppRoomDatabase.getInstance(context)!!
    }

}