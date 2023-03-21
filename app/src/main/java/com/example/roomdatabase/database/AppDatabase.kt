package com.example.roomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabase.model.Employee
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Database(entities = [Employee::class], version = 1)
abstract class AppDatabase:RoomDatabase()
{

    abstract fun getDao():EmployeeDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getAppDB(context: Context):AppDatabase{
            val tempInstance= INSTANCE

            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database")
                    .build()
                INSTANCE =instance
                return instance
            }
        }
    }

}