package com.example.roomdatabase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.roomdatabase.database.EmployeeDao
import com.example.roomdatabase.model.Employee


//Android ROOM Database | ViewModel, LiveData, RecyclerView Tutorial using Kotlin
//LeaningWorld
class UserViewModel(passedDao:EmployeeDao, application: Application):AndroidViewModel(application) {

    private val userDao : EmployeeDao = passedDao

    private var allUsers : MutableLiveData<List<Employee>> = MutableLiveData()

    init {
        getAllData()
    }

    fun getAllUsersObservers():MutableLiveData<List<Employee>>{
        return allUsers
    }
    //getting all data
    private fun getAllData(){
        val list = userDao.getAll()
        allUsers.postValue(list)
    }
    //inserting new data to database
    fun insertUser(entity: Employee){
        userDao.insert(entity)
        getAllData()
    }
    //finding specific data from database
    fun getUserByID(empId : Int): Employee {
        userDao.findById(empId)
        return userDao.findById(empId)
    }
    //deleting employee data from passed data
    fun deleteUser(entity: Employee){
        userDao.delete(entity)
        getAllData()
    }

}