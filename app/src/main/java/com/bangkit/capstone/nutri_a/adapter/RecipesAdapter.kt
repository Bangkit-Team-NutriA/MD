package com.bangkit.capstone.nutri_a.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.model.DataRecipes
import com.bangkit.capstone.nutri_a.ui.recipes.DetailRecipesActivity
import com.bumptech.glide.Glide


class RecipesAdapter (private val listRecipes: ArrayList<DataRecipes>) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvName: TextView = view.findViewById(R.id.tv_name)
        val ivImage: ImageView = view.findViewById(R.id.iv_photo)
        val btnDetail: Button = view.findViewById(R.id.btn_detail)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.recipes_item, viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvName.text = listRecipes[position].Nama

        val photo = listRecipes[position].Url.toString()
        val url = photo.lowercase()

        Glide.with(viewHolder.itemView.context)
            .load(url)
            .into(viewHolder.ivImage)

        viewHolder.btnDetail.setOnClickListener {
            val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                viewHolder.itemView.context as Activity,
                Pair(viewHolder.ivImage, "img_photo_detail_transition")
            )

            val intent = Intent(viewHolder.itemView.context, DetailRecipesActivity::class.java)

            val recipes =  listRecipes[position]
            intent.putExtra(DetailRecipesActivity.TAG, recipes)

            viewHolder.itemView.context.startActivity(intent, optionsCompat.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return listRecipes.size
    }
}