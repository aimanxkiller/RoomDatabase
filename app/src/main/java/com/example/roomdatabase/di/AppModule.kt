package com.example.roomdatabase.di

import android.content.Context
import com.example.roomdatabase.database.AppDatabase
import com.example.roomdatabase.database.EmployeeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getAppDB(context:Context):AppDatabase{
        return AppDatabase.getAppDB(context)
    }

    fun getDao(appDB:AppDatabase): EmployeeDao {
        return appDB.getDao()
    }
}