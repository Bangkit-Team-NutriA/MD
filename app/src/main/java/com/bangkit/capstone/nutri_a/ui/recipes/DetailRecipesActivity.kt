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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = R.string.title_detail_recipes.toString()

        val recipes = intent.getParcelableExtra<DataRecipes>(TAG) as DataRecipes

        Glide.with(this)
            .load(recipes.Url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imgPhoto)

        binding.tvName.text = recipes.Nama

        binding.tvCarbohydrateValue.text = recipes.nutrisi?.karbohidratCHO

        binding.tvDescriptionValue.text = recipes.Deskripsi

        val filtered1 = "[]"
        val ingredients1 = recipes.Bahan.toString().filterNot { filtered1.indexOf(it) > -1 }
        val ingredients2 = recipes.Bahan2.toString().filterNot { filtered1.indexOf(it) > -1 }
        val ingredients = "$ingredients1, $ingredients2"
        binding.tvIngredientsValue.text = ingredients

        val cara = recipes.Cara.toString().filterNot { filtered1.indexOf(it) > -1 }
        val direction = cara.replace(", ", "\n")
        binding.tvMethodValue.text = direction


    }




    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val TAG = "DetailRecipes"
    }
}