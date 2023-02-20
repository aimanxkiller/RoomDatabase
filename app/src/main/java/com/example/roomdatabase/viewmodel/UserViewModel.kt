package com.example.roomdatabase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.roomdatabase.database.AppDatabase
import com.example.roomdatabase.database.EmployeeDao
import com.example.roomdatabase.model.Employee


//Android ROOM Database | ViewModel, LiveData, RecyclerView Tutorial using Kotlin
//LeaningWorld
@Suppress("UNREACHABLE_CODE")
class UserViewModel(application: Application):AndroidViewModel(application) {

    private val userDao : EmployeeDao

    private var allUsers : MutableLiveData<List<Employee>>

    init {
        val database = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "my-database"
        ).build()

        userDao = database.employeeDao()

        allUsers = MutableLiveData()
        getAllData()
    }

    fun getAllUsersObservers():MutableLiveData<List<Employee>>{
        return allUsers
    }

    private fun getAllData(){
        val userDao = AppDatabase.getDatabase(getApplication()).employeeDao()
        val list = userDao.getAll()

        allUsers.postValue(list)
    }

    fun insertUser(entity: Employee){
        val userDao = AppDatabase.getDatabase(getApplication()).employeeDao()
        userDao.findById(1)
        userDao.insert(entity)
        getAllData()
    }

    fun updateUser(entity: Employee){
        val userDao = AppDatabase.getDatabase(getApplication()).employeeDao()
        userDao.updateInfo(entity.name.toString(), entity.id.toString().toInt())
        getAllData()
    }

    fun getUserByID(empId : Int): Employee {
        val userDao = AppDatabase.getDatabase(getApplication()).employeeDao()
        userDao.findById(empId)
        return userDao.findById(empId)
        getAllData()
    }

    fun deleteUser(entity: Employee){
        val userDao = AppDatabase.getDatabase(getApplication()).employeeDao()
        userDao.delete(entity)
        getAllData()
    }

}