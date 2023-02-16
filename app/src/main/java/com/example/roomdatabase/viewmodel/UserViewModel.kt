package com.example.roomdatabase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.roomdatabase.AppDatabase
import com.example.roomdatabase.EmployeeDao
import com.example.roomdatabase.model.Employee


//Android ROOM Database | ViewModel, LiveData, RecyclerView Tutorial using Kotlin
//LeaningWorld
class UserViewModel(application: Application):AndroidViewModel(application) {
    private val userDao : EmployeeDao
    private lateinit var allUsers : MutableLiveData<List<Employee>>
    init {
        val database = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "my-database"
        ).build()

        userDao = database.employeeDao()

        allUsers = MutableLiveData()
    }

    fun getAllUsersObservers():MutableLiveData<List<Employee>>{
        return allUsers
    }

    fun getdata(){
        val userDao = AppDatabase.getDatabase((getApplication())).employeeDao()
        val list = userDao.getAll()

        allUsers.postValue(list)
    }



}