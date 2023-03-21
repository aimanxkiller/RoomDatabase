package com.example.roomdatabase.database

import com.example.roomdatabase.model.Employee
import javax.inject.Inject

class RoomRepository @Inject constructor(private val appDao:EmployeeDao) {

    fun getData():List<Employee>{
        return appDao.getAll()
    }

    fun insertData(emp :Employee){
        appDao.insert(emp)
    }
}