package com.herbamate.herbamate.view.pages.symptom_checker

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
                getString(R.string.headache),
                getString(R.string.nauseous),
                getString(R.string.muscle_pain),
                getString(R.string.fever),
                getString(R.string.cough),
                getString(R.string.tired),
                getString(R.string.bloating),
                getString(R.string.cramps)
            )

            "external" -> listOf(
                getString(R.string.dizzy),
                getString(R.string.dry_throat),
                getString(R.string.stomach_ache),
                getString(R.string.cough),
                getString(R.string.cold),
                getString(R.string.tired),
                getString(R.string.nauseous),
                getString(R.string.headache)
            )

            "neural" -> listOf(
                getString(R.string.cramps),
                getString(R.string.stomach_ache),
                getString(R.string.tired),
                getString(R.string.muscle_pain),
                getString(R.string.fever),
                getString(R.string.dizzy),
                getString(R.string.cough),
                getString(R.string.dry_throat)
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
                    selectedSymptoms.add(
                        when (child.text) {
                            getString(R.string.headache) -> "Sakit kepala"
                            getString(R.string.nauseous) -> "Mual"
                            getString(R.string.muscle_pain) -> "Nyeri otot"
                            getString(R.string.fever) -> "Demam"
                            getString(R.string.cough) -> "Batuk"
                            getString(R.string.tired) -> "Lelah"
                            getString(R.string.bloating) -> "Kembung"
                            getString(R.string.cramps) -> "Kram"
                            getString(R.string.dizzy) -> "Pusing"
                            getString(R.string.dry_throat) -> "Tenggorokan kering"
                            getString(R.string.stomach_ache) -> "Sakit perut"
                            getString(R.string.cold) -> "Pilek"
                            else -> ""
                        }
                    )
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