package com.example.roomdatabase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private var allUsers : MutableLiveData<List<Employee>> = MutableLiveData()

    init {
        loadRepository()
    }

    fun getAllUsersObservers():MutableLiveData<List<Employee>>{
        return allUsers
    }

    private fun loadRepository(){
        val list = repository.getData()
        allUsers.postValue(list)
    }

    fun insertRepo(emp:Employee){
        repository.insertData(emp)
        loadRepository()
    }

    fun getUserByID(empId : Int): Employee {
        return repository.findById(empId)
    }
    //deleting employee data from passed data
    fun deleteUser(entity: Employee){
        repository.delete(entity)
        loadRepository()
    }

}