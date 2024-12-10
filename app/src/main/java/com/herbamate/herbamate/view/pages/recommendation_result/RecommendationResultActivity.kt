package com.herbamate.herbamate.view.pages.recommendation_result

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbamate.herbamate.R
import com.herbamate.herbamate.databinding.ActivityRecommendationResultBinding
import com.herbamate.herbamate.utils.Result
import com.herbamate.herbamate.utils.factory.ViewModelFactory
import com.herbamate.herbamate.view.MainActivity
import com.herbamate.herbamate.view.pages.detail.DetailActivity
import com.herbamate.herbamate.view.pages.result.ResultAdapter
import com.herbamate.herbamate.view.pages.result.ResultViewModel

class RecommendationResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationResultBinding
    private lateinit var viewModel: RecommendationResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationResultBinding.inflate(layoutInflater)
        viewModel = obtainViewModel(this)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        binding.rvResultRecommendation.layoutManager = LinearLayoutManager(this)

        val symptomList = binding.descriptionSelectedSymptom

        val selectedSymptoms = intent.getStringArrayListExtra("SELECTED_SYMPTOMS") ?: listOf()

        if (savedInstanceState == null){
            viewModel.getRecommendation(selectedSymptoms)
        }

        symptomList.text = selectedSymptoms.joinToString("\n") { "✔ $it" }
        symptomList.setTextColor(Color.BLACK)

        viewModel.herb.observe(this) { result ->
            when(result){
                is Result.Error -> {
                    showLoading(false)
                    showErrorDialog(result.error)
                }
                Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    val adapter = RecommendationResultAdapter()
                    adapter.setHerbList(result.data)
                    binding.rvResultRecommendation.adapter = adapter
                }
            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.recommendationLayout.visibility = if (isLoading) View.GONE else View.VISIBLE
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

    private fun obtainViewModel(context: Context): RecommendationResultViewModel {
        val factory = ViewModelFactory.getInstance(context)
        return ViewModelProvider(this, factory)[RecommendationResultViewModel::class.java]
    }
}