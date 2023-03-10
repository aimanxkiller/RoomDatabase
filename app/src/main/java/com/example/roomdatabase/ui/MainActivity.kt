package com.example.roomdatabase.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.roomdatabase.adapter.RecyclerLiveViewAdapter
import com.example.roomdatabase.databinding.ActivityMainBinding
import com.example.roomdatabase.model.Employee
import com.example.roomdatabase.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //private lateinit var appDb: AppDatabase

    lateinit var recyclerViewAdapter:RecyclerLiveViewAdapter
    lateinit var viewModel: UserViewModel
    private lateinit var imm:InputMethodManager

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        buttonSettings()

        binding.recyclerMain.apply {
            layoutManager = GridLayoutManager(this@MainActivity,3)
            recyclerViewAdapter = RecyclerLiveViewAdapter()
            adapter = recyclerViewAdapter
        }

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))[UserViewModel::class.java]

        viewModel.getAllUsersObservers().observe(this) {
            recyclerViewAdapter.setListData(ArrayList(it))
            recyclerViewAdapter.notifyDataSetChanged()
        }

    }

    private fun buttonSettings(){
        binding.mainButton1.setOnClickListener {
            saveLive()
        }
        binding.buttonDelete.setOnClickListener {
            deleteLive()
        }
    }

    private fun saveLive(){
        val name = binding.editTextName.text.toString()
        val department = binding.editTextDepartment.text.toString()
        val id = binding.editTextTextId.text.toString()

        if (name.isNotEmpty()&&department.isNotEmpty()&&id.isNotEmpty()){
            lifecycleScope.launch(Dispatchers.Main) {
                val newData = Employee(null, name, department, id.toInt())
                async {
                    viewModel.insertUser(newData)
                }
                Toast.makeText(this@MainActivity, "New Data Added", Toast.LENGTH_SHORT).show()
                binding.editTextName.text.clear()
                binding.editTextDepartment.text.clear()
                binding.editTextTextId.text.clear()
                clearFocus()
            }
        }else{
            Toast.makeText(this@MainActivity,"Please Fill All Data",Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFocus(){
        binding.root.apply {
            val views = touchables
            for (view in views){
                view.clearFocus()
            }
            imm.hideSoftInputFromWindow(binding.editTextDepartment.windowToken, 0)
        }
    }

    private fun deleteLive(){
        val id = binding.textViewDelete.text.toString()
        if (id.isNotEmpty()) {
            lifecycleScope.launch(Dispatchers.Main){
                val toDelete = async{ viewModel.getUserByID(id.toInt()) }
                if (toDelete.await() != null) {
                    async {  viewModel.deleteUser(toDelete.await())}
                    Toast.makeText(this@MainActivity, "User found and deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "No user found", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this@MainActivity,"Please Insert User EmpID",Toast.LENGTH_SHORT).show()
        }
        binding.textViewDelete.text.clear()
        binding.textViewDelete.clearFocus()
    }

    /* Old inset,get database and delete
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
    */
}