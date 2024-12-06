package com.herbamate.herbamate.view.pages.checker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.herbamate.herbamate.R
import com.herbamate.herbamate.databinding.ActivitySymptomCheckerBinding
import com.herbamate.herbamate.view.pages.recommendation_result.RecommendationResultActivity

class SymptomCheckerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySymptomCheckerBinding
    private lateinit var symptomCheckerViewModel: SymptomCheckerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySymptomCheckerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val symptomsLayout = binding.symptomLayout
        val btnFinish = binding.finishButton

        val category = intent.getStringExtra("CATEGORY")

        val symptoms = when (category) {
            "internal" -> listOf(
                "Sakit kepala", "Mual", "Nyeri otot", "Demam", "Batuk", "Lelah", "Kembung", "Kram"
            )

            "external" -> listOf(
                "Pusing",
                "Tenggorokan kering",
                "Sakit perut",
                "Batuk",
                "Pilek",
                "Lelah",
                "Mual",
                "Sakit kepala"
            )

            "neural" -> listOf(
                "Kram",
                "Sakit perut",
                "Lelah",
                "Nyeri otot",
                "Demam",
                "Pusing",
                "Batuk",
                "Tenggorokan kering"
            )

            else -> listOf()
        }

        symptoms.forEach { symptom ->
            val checkBox = CheckBox(this).apply {
                text = symptom
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 16, 0, 16)
                layoutParams = params
            }
            symptomsLayout.addView(checkBox)
        }

        if (savedInstanceState != null) {
            val savedCheckedItems = savedInstanceState.getStringArrayList("SELECTED_SYMPTOMS")
            savedCheckedItems?.forEach { symptom ->
                symptomsLayout.children
                    .filterIsInstance<CheckBox>()
                    .firstOrNull { it.text == symptom }?.isChecked = true
            }
        }

        btnFinish.setOnClickListener {
            val selectedSymptoms = mutableListOf<String>()

            for (i in 0 until symptomsLayout.childCount) {
                val child = symptomsLayout.getChildAt(i)
                if (child is CheckBox && child.isChecked) {
                    selectedSymptoms.add(child.text.toString())
                }
            }

            if (selectedSymptoms.isEmpty()) {
                Toast.makeText(this, getString(R.string.choose_symptom), Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, RecommendationResultActivity::class.java)
                intent.putStringArrayListExtra("SELECTED_SYMPTOMS", ArrayList(selectedSymptoms))
                startActivity(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val checkedItems = mutableListOf<String>()
        val symptomsLayout = binding.symptomLayout

        for (i in 0 until symptomsLayout.childCount) {
            val child = symptomsLayout.getChildAt(i)
            if (child is CheckBox && child.isChecked) {
                checkedItems.add(child.text.toString())
            }
        }
        outState.putStringArrayList("SELECTED_SYMPTOMS", ArrayList(checkedItems))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}