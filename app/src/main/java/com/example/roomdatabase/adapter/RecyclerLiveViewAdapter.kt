package com.example.roomdatabase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.model.Employee

class RecyclerLiveViewAdapter :RecyclerView.Adapter<RecyclerLiveViewAdapter.MyViewHolder>() {

    private var items = ArrayList<Employee>()

    fun setListData(data:ArrayList<Employee>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.rows_item,parent,false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //setting values
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {

        val name: TextView = itemView.findViewById(R.id.textView1)
        private val department: TextView = itemView.findViewById(R.id.textView2)
        private val empId: TextView = itemView.findViewById(R.id.textView3)

        fun bind(data:Employee){
            name.text = data.name
            department.text = data.department
            empId.text = data.empId.toString()
        }
    }

}