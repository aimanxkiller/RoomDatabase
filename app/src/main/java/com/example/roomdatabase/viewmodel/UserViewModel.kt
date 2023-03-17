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
class UserViewModel(passedDao:EmployeeDao, application: Application):AndroidViewModel(application) {

    private val userDao : EmployeeDao = passedDao

    private var allUsers : MutableLiveData<List<Employee>> = MutableLiveData()

    init {
        getAllData()
    }

    fun getAllUsersObservers():MutableLiveData<List<Employee>>{
        return allUsers
    }

    private fun getAllData(){
        val list = userDao.getAll()
        allUsers.postValue(list)
    }

    fun insertUser(entity: Employee){
        userDao.insert(entity)
        getAllData()
    }

    fun updateUser(entity: Employee){
        userDao.updateInfo(entity.name.toString(), entity.id.toString().toInt())
        getAllData()
    }

    fun getUserByID(empId : Int): Employee {
        userDao.findById(empId)
        return userDao.findById(empId)
        getAllData()
    }

    fun deleteUser(entity: Employee){
        userDao.delete(entity)
        getAllData()
    }

}