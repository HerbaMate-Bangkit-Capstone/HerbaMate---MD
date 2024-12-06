package com.herbamate.herbamate.view.pages.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.herbamate.herbamate.R
import com.herbamate.herbamate.databinding.ActivityDetailBinding
import com.herbamate.herbamate.utils.factory.ViewModelFactory
import com.herbamate.herbamate.view.pages.favorite.FavoriteViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val factory = ViewModelFactory.getInstance(applicationContext)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        val herbalId = intent.getStringExtra("HERBAL_ID") ?: ""

        herbalId.let { id ->
            favoriteViewModel.getFavorite(id).observe(this) { favorite ->
                if (favorite == null) {
                    binding.favoriteIcon.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    binding.favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                }

//                binding.favoriteIcon.setOnClickListener {
//                    if (favorite == null) {
//                        detailViewModel.detailHerbal.value?.let { herbal ->
//                            favoriteViewModel.insertFavorite(
//                                Favorite(
//                                    id = id,
//
//                                )
//                            )
//                        }
//                    } else {
//                        favoriteViewModel.deleteFavorite(favorite)
//                    }
//                }
            }
        }

        detailViewModel.fetchDetailEvent(herbalId)

//        detailViewModel.detailHerbal.observe(this, { result ->
//            when (result) {
//                is Result.Loading -> {
//                    showLoading(true)
//                }
//                is Result.Success -> {
//                    showLoading(false)
//                    binding.nameHerbal.text = result
//                    binding.nameLokal.text = result
//                    Glide.with(this)
//                        .load(result)
//                        .into(binding.imageHerbal)
//                    binding.nameLokal.text = result
//                    binding.descriptionHerbal.text = result
//                    binding.descriptionUtility.text = result
//                    binding.descriptionComposition.text = result
//                }
//                is Result.Error -> {
//                    showLoading(false)
//                    Toast.makeText(this, result.error, Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//
//        detailViewModel.isLoading.observe(this, { isLoading ->
//            if (isLoading) {
//                showLoading(true)
//            } else {
//                showLoading(false)
//            }
//        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}