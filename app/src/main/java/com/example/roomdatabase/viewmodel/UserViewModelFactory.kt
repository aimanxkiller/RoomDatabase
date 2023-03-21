package com.example.roomdatabase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdatabase.database.AppDatabase

//class UserViewModelFactory(private val myDatabase: AppDatabase):ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
//            return UserViewModel(myDatabase.employeeDao()) as T
//        }
//        throw  IllegalArgumentException("Unknown View Model Class")
//    }
//}