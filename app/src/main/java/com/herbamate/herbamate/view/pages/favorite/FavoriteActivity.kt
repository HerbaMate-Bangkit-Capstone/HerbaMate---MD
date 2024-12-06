package com.herbamate.herbamate.view.pages.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbamate.herbamate.R
import com.herbamate.herbamate.databinding.ActivityFavoriteBinding
import com.herbamate.herbamate.utils.Result
import com.herbamate.herbamate.utils.factory.ViewModelFactory
import com.herbamate.herbamate.view.pages.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    companion object {
        const val HERBAL_ID = "HERBAL_ID"
    }

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.herbal_favorite)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        favoriteViewModel = ViewModelProvider(
            this, ViewModelFactory.getInstance(this)
        )[FavoriteViewModel::class.java]
        favoriteAdapter = FavoriteAdapter { herbalId ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(HERBAL_ID, herbalId)
            }
            startActivity(intent)
        }

        binding.rvHerbal.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@FavoriteActivity, LinearLayoutManager.VERTICAL
                )
            )
            adapter = favoriteAdapter
        }

        favoriteViewModel.favoriteResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    favoriteAdapter.submitList(result.data)
                    if (result.data.isEmpty()) {
                        showNoFavoriteMessage(true)
                    } else {
                        showNoFavoriteMessage(false)
                    }
                }

                is Result.Error -> {
                    showLoading(false)
                    favoriteAdapter.submitList(null)
                    showNoFavoriteMessage(true)
                }
            }
        }

        favoriteViewModel.fetchFavorite()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNoFavoriteMessage(isVisible: Boolean) {
        binding.textNoFavorite.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}