package com.bangkit.capstone.nutri_a.ui.recipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.databinding.ActivityDetailRecipesBinding
import com.bangkit.capstone.nutri_a.model.DataRecipes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class DetailRecipesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailRecipesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipes = intent.getParcelableExtra<DataRecipes>(TAG) as DataRecipes

        Glide.with(this)
            .load(recipes.Url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imgPhoto)

        binding.tvName.text = recipes.Nama

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val TAG = "DetailRecipes"
    }
}