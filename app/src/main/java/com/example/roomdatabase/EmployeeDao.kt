package com.example.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee_table")
    fun getAll():List<Employee>

    @Query("SELECT * FROM employee_table WHERE employeeId LIKE :empId LIMIT 1")
    fun findById(empId:Int):Employee

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(employee: Employee)

    @Delete
    fun delete(employee: Employee):Int

    @Query("DELETE FROM employee_table WHERE employeeId = :empId")
    fun deleteWhenId(empId:Int ):Int

    @Query("DELETE FROM employee_table")
    fun deleteAll()

}