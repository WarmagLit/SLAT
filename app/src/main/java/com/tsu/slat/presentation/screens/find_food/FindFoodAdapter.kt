package com.tsu.slat.presentation.screens.find_food

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsu.slat.R
import com.tsu.slat.data.entity.Food
import com.tsu.slat.data.entity.FoodItemShort
import com.tsu.slat.databinding.FoodInfoLayoutBinding

class FindFoodAdapter(val viewModel: FindFoodViewModel): RecyclerView.Adapter<FindFoodAdapter.FoodsHolder>(){

    val foodList = ArrayList<FoodItemShort>()

    inner class FoodsHolder(item: View): RecyclerView.ViewHolder(item),
        View.OnClickListener {
        val binding = FoodInfoLayoutBinding.bind(item)

        fun bind(food: FoodItemShort) = with(binding){
            txtMealName.text = food.food_name
            txtMealBrand.text = food.food_description

            layoutFoodInfo.setOnClickListener {
                Log.d("get", "DETAILED INFO")
                viewModel.getDetailedInfo(food)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //Nothing
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_info_layout, parent, false)
        return FoodsHolder(view)
    }

    override fun onBindViewHolder(holder: FoodsHolder, position: Int) {
        holder.bind(foodList[position])

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun addFood(food: FoodItemShort) {
        foodList.add(food)
        notifyDataSetChanged()
    }

    fun clearList() {
        foodList.clear()
        notifyDataSetChanged()
    }
}