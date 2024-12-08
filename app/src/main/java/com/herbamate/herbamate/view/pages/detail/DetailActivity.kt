package com.herbamate.herbamate.view.pages.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.herbamate.herbamate.R
import com.herbamate.herbamate.data.local.entity.Favorite
import com.herbamate.herbamate.databinding.ActivityDetailBinding
import com.herbamate.herbamate.model.Herb
import com.herbamate.herbamate.utils.Result
import com.herbamate.herbamate.utils.factory.ViewModelFactory
import com.herbamate.herbamate.view.pages.favorite.FavoriteViewModel
import com.herbamate.herbamate.view.pages.home.HomeViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var favoriteHerb: Favorite? = null
    private lateinit var currentHerb: Herb

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

        viewModel = obtainViewModel(this)


        val herbalId = intent.getIntExtra(EXTRA_ID, -1)

        if (savedInstanceState === null) {
            viewModel.fetchDetailEvent(herbalId)
        }

        viewModel.herb.observe(this) { result ->
            when (result) {
                is Result.Error -> {
                    showLoading(false)
                    showErrorDialog(result.error)
                }

                Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    binding.nameHerbal.text = result.data.name
                    binding.herbalLatin.text = result.data.latinName

                    Glide.with(this)
                        .load(result.data.imageLink)
                        .error(R.drawable.ic_place_holder)
                        .into(binding.imageHerbal)
                    var localNameMerge = ""
                    for (i in 0..<result.data.localName.size) {
                        if (i == result.data.localName.size - 1) {
                            localNameMerge += result.data.localName[i]
                            break
                        }
                        localNameMerge = localNameMerge + result.data.localName[i] + "\n"
                    }
                    binding.nameLocal.text = localNameMerge
                    binding.descriptionHerbal.text = result.data.description
                    var utilityHerbsMerge = ""
                    for (i in 0..<result.data.disease.size) {
                        if (i == result.data.disease.size - 1) {
                            utilityHerbsMerge = utilityHerbsMerge + "• " + result.data.disease[i]
                            break
                        }
                        utilityHerbsMerge = utilityHerbsMerge + "• " + result.data.disease[i] + "\n"
                    }

                    binding.descriptionUtility.text = utilityHerbsMerge
                    binding.descriptionComposition.text = result.data.composition
                    currentHerb = Herb(
                        id = result.data.id,
                        name = result.data.name,
                        latinName = result.data.latinName,
                        imageLink = result.data.imageLink,
                        description = ""
                    )
                }
            }
        }

        viewModel.getIsFavoriteStatus(id = herbalId).observe(this) {
            favoriteHerb = it
            if (favoriteHerb == null) {
                binding.favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                return@observe
            }
            binding.favoriteIcon.setImageResource(R.drawable.baseline_favorite_24)
        }

        binding.favoriteIcon.setOnClickListener {
            if (favoriteHerb == null) {
                viewModel.insertFavoriteHerb(
                    Favorite(
                        id = currentHerb.id,
                        herbalName = currentHerb.name,
                        herbalLatin = currentHerb.latinName,
                        herbalImage = currentHerb.imageLink,
                        herbalDescription = currentHerb.description
                    )
                )
                return@setOnClickListener
            }
            viewModel.deleteFavoriteHerb(favoriteHerb!!)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(context: Context): DetailViewModel {
        val factory = ViewModelFactory.getInstance(context)
        return ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }

    private fun showErrorDialog(message: String, onFinish: () -> Unit = { finish() }) {
        AlertDialog.Builder(this).apply {
            setTitle(resources.getString(R.string.error))
            setMessage(message)
            setPositiveButton(resources.getString(R.string.okay)) { _, _ ->
                onFinish()
            }
            create()
            show()
        }
    }

    companion object {
        const val EXTRA_ID = "id"
    }
}