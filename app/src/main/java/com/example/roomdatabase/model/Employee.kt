package com.example.roomdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_table")
data class Employee(
    @PrimaryKey(autoGenerate = true)val id:Int?,
    @ColumnInfo(name = "name")val name:String?,
    @ColumnInfo(name = "department")val department:String?,
    @ColumnInfo(name = "employeeId")val empId:Int?
)
