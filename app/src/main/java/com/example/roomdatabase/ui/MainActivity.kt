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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter:RecyclerLiveViewAdapter
    private  val viewModel: UserViewModel = ViewModelProvider(this@MainActivity)[UserViewModel::class.java]
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

        //updated to use Hilt injection for database
            viewModel.getAllUsersObservers().observe(this@MainActivity){
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
                viewModel.insertRepo(newData)
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

    //Assist to clear focus
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
                    viewModel.deleteUser(toDelete.await())
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

}