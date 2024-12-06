package com.herbamate.herbamate.view.pages.category

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.herbamate.herbamate.R
import com.herbamate.herbamate.databinding.ActivitySymptomCategoryBinding
import com.herbamate.herbamate.view.pages.checker.SymptomCheckerActivity

class SymptomCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySymptomCategoryBinding
    private lateinit var symptomCategoryViewModel: SymptomCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySymptomCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val radioGroup = binding.radioGroup
        val btnNext = binding.nextButton

        btnNext.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = binding.root.findViewById<RadioButton>(selectedId)

            val category = when (selectedRadioButton?.id) {
                binding.radioInternal.id -> "internal"
                binding.radioExternal.id -> "external"
                binding.radioNeural.id -> "neural"
                else -> null
            }

            if (category != null) {
                val intent = Intent(this, SymptomCheckerActivity::class.java)
                intent.putExtra("CATEGORY", category)
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.choose_category), Toast.LENGTH_SHORT).show()
            }
        }
    }
}