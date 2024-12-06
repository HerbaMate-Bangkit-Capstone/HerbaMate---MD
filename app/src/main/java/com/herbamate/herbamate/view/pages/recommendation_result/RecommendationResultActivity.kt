package com.herbamate.herbamate.view.pages.recommendation_result

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.herbamate.herbamate.databinding.ActivityRecommendationResultBinding
import com.herbamate.herbamate.view.MainActivity
import com.herbamate.herbamate.view.pages.detail.DetailActivity

class RecommendationResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationResultBinding
    private lateinit var recommendationResultViewModel: RecommendationResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationResultBinding.inflate(layoutInflater)
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

        val symptomList = binding.descriptionSelectedSymptom

        val selectedSymptoms = intent.getStringArrayListExtra("SELECTED_SYMPTOMS") ?: listOf()

        symptomList.text = selectedSymptoms.joinToString("\n") { "✔ $it" }
        symptomList.setTextColor(Color.BLACK)

        binding.rvResults.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

//        binding.rvResults.adapter = adapter
//        binding.rvResults.layoutManager = LinearLayoutManager(this)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}