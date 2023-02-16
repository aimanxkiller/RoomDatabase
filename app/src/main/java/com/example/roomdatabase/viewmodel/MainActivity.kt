package com.example.roomdatabase.viewmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.roomdatabase.AppDatabase
import com.example.roomdatabase.model.Employee
import com.example.roomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appDb: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDatabase()
        buttonSettings()

    }

    private fun buttonSettings(){
        binding.mainButton1.setOnClickListener {
            writeData()
        }

        binding.mainButton2.setOnClickListener {
            val intent = Intent(this, DisplayAllActivity::class.java)
            startActivity(intent)
        }

        binding.buttonDelete.setOnClickListener {
            deleteData()
        }
    }

    private fun deleteData(){
        val id:String = binding.textViewDelete.text.toString()
        if (id.isNotEmpty()){
            lifecycleScope.launch(Dispatchers.IO){
                appDb.employeeDao().deleteWhenId(id.toInt())
            }
            binding.textViewDelete.text.clear()
        }else{
            Toast.makeText(this,"Please enter ID",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDatabase(){
        lifecycleScope.launch(Dispatchers.IO){
            appDb= AppDatabase.getDatabase(this@MainActivity)
            appDb.employeeDao().getAll()
        }
    }

    private fun writeData(){
        val name = binding.editTextName.text.toString()
        val department = binding.editTextDepartment.text.toString()
        val id = binding.editTextTextId.text.toString()

        if (name.isNotEmpty()&&department.isNotEmpty()&&id.isNotEmpty()){
            val employee = Employee(
                null,name,department,id.toInt()
            )
            lifecycleScope.launch(Dispatchers.IO){
                appDb.employeeDao().insert(employee)
            }

            binding.editTextName.text.clear()
            binding.editTextDepartment.text.clear()
            binding.editTextTextId.text.clear()

            Toast.makeText(this@MainActivity,"Inserted Data",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@MainActivity,"Plase Fill All Data",Toast.LENGTH_SHORT).show()
        }
    }

}