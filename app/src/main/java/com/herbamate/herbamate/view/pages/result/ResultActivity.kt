package com.herbamate.herbamate.view.pages.result

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.herbamate.herbamate.R
import com.herbamate.herbamate.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val keyword = intent.getStringExtra(EXTRA_KEYWORD)

        binding.resultSearchText.setText(keyword)
        binding.resultSearchText.setOnKeyListener { view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_UP){
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(EXTRA_KEYWORD, binding.resultSearchText.text.toString())
                startActivity(intent)
                true
            }
            false
        }
    }

    private fun initComponent() {
        binding = ActivityResultBinding.inflate(layoutInflater)
    }

    companion object{
        const val EXTRA_KEYWORD = "keyword"
    }
}