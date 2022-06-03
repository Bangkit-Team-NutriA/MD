package com.bangkit.capstone.nutri_a.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.model.DataFood

class FoodAdapter(private val listFood: ArrayList<DataFood>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCarbo: TextView = view.findViewById(R.id.tv_carbohydrate)
        val tvProtein: TextView = view.findViewById(R.id.tv_protein)
        val tvFat: TextView = view.findViewById(R.id.tv_fat)
        val tvCalories: TextView = view.findViewById(R.id.tv_calories)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.food_item, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvName.text = listFood[position].name
        viewHolder.tvCarbo.text = listFood[position].carbo
        viewHolder.tvProtein.text = listFood[position].protein
        viewHolder.tvFat.text = listFood[position].fat
        viewHolder.tvCalories.text = listFood[position].calories


    }

    override fun getItemCount(): Int {
        return listFood.size
    }
}