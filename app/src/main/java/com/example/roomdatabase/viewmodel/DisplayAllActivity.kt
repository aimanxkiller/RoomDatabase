package com.example.roomdatabase.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.AppDatabase
import com.example.roomdatabase.model.Employee
import com.example.roomdatabase.adapter.MyAdapter
import com.example.roomdatabase.databinding.ActivityDisplayAllBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DisplayAllActivity : AppCompatActivity() {

    private lateinit var appDb: AppDatabase
    private lateinit var binding: ActivityDisplayAllBinding
    private lateinit var data:ArrayList<Employee>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDatabase()
        setViews()
    }

    private fun getDatabase(){
        lifecycleScope.launch(Dispatchers.IO){
            appDb= AppDatabase.getDatabase(this@DisplayAllActivity)
        }
    }

    private fun setViews(){
        val recyclerView = binding.listView
        recyclerView.setHasFixedSize(true)
        val linerManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linerManager

        lifecycleScope.launch(Dispatchers.IO){
            val dataX = async {
                return@async appDb.employeeDao().getAll()
            }
            data = dataX.await() as ArrayList<Employee>
            recyclerView.apply {
                val myAdapter = MyAdapter(baseContext,data)
                adapter = myAdapter
            }
        }
    }
}