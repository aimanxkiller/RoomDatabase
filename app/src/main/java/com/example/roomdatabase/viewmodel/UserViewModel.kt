package com.example.roomdatabase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomdatabase.database.AppDatabase
import com.example.roomdatabase.database.EmployeeDao
import com.example.roomdatabase.database.RoomRepository
import com.example.roomdatabase.model.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


//Android ROOM Database | ViewModel, LiveData, RecyclerView Tutorial using Kotlin
//LeaningWorld
@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: RoomRepository)
    : ViewModel() {

//    private val userDao : EmployeeDao = database.employeeDao()
    private var allUsers : MutableLiveData<List<Employee>>

    init {
        allUsers = MutableLiveData()
    }

    fun getAllUsersObservers():MutableLiveData<List<Employee>>{
        return allUsers
    }

    fun loadRepository(){
        val list = repository.getData()
        allUsers.postValue(list)
    }
    fun insertRepo(emp:Employee){
        repository.insertData(emp)
    }

//    //getting all data
//    private fun getAllData(){
//        val list = userDao.getAll()
//        allUsers.postValue(list)
//    }
//    //inserting new data to database
//    fun insertUser(entity: Employee){
//        userDao.insert(entity)
//        getAllData()
//    }
//    //finding specific data from database
//    fun getUserByID(empId : Int): Employee {
//        userDao.findById(empId)
//        return userDao.findById(empId)
//    }
//    //deleting employee data from passed data
//    fun deleteUser(entity: Employee){
//        userDao.delete(entity)
//        getAllData()
//    }

}