package com.example.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.databinding.ActivityDisplayAllBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DisplayAllActivity : AppCompatActivity() {

    private lateinit var appDb:AppDatabase
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
            appDb=AppDatabase.getDatabase(this@DisplayAllActivity)
        }
    }

    fun setViews(){
        val recyclerView = binding.listView
        recyclerView.setHasFixedSize(true)
        val linerManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linerManager

        lifecycleScope.launch(Dispatchers.IO){
            var datax = async {
                return@async appDb.employeeDao().getAll()
            }
            data = datax.await() as ArrayList<Employee>
            val myAdapter = MyAdapter(baseContext,data)
            recyclerView.adapter = myAdapter
        }
    }
}