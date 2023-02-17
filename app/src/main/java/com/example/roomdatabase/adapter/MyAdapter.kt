package com.example.roomdatabase.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.model.Employee
import com.example.roomdatabase.R

class MyAdapter(private val baseContext: Context, private val data: ArrayList<Employee>)
    :RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var name: TextView
         var department: TextView
         var empId:TextView
        init {
            name = itemView.findViewById(R.id.textView1)
            department = itemView.findViewById(R.id.textView2)
            empId = itemView.findViewById(R.id.textView3)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(baseContext)
            .inflate(R.layout.rows_item,parent,false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = "Name : "+data[position].name
        holder.department.text = "Department : "+data[position].department
        holder.empId.text = "Employee ID :"+data[position].empId.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}