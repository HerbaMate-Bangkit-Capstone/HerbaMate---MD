package com.herbamate.herbamate.view.pages.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.herbamate.herbamate.R
import com.herbamate.herbamate.databinding.ActivityResultBinding
import com.herbamate.herbamate.utils.Result
import com.herbamate.herbamate.utils.factory.ViewModelFactory
import com.herbamate.herbamate.view.pages.detail.DetailViewModel
import com.herbamate.herbamate.view.pages.home.HomeAdapter
import com.herbamate.herbamate.view.pages.home.HomeViewModel
import retrofit2.HttpException

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel : ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.rvResultHerbs.layoutManager = LinearLayoutManager(this)
        binding.rvResultHerbs.isNestedScrollingEnabled = false

        val keyword = intent.getStringExtra(EXTRA_KEYWORD)

        if (savedInstanceState == null){
            viewModel.getHerbSearch(keyword ?: "")
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.herb.observe(this) { result ->
            when (result){
                is Result.Error -> {
                    showLoading(false)
                    if (result.error == "404") {
                        binding.rvResultHerbs.visibility = View.GONE
                        binding.notFound.visibility = View.VISIBLE
                    } else {
                        showErrorDialog(result.error)
                    }
                }
                Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    val adapter = ResultAdapter()
                    adapter.setHerbList(result.data)
                    binding.rvResultHerbs.adapter = adapter
                }
            }
        }


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
        viewModel = obtainViewModel(this)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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

    private fun obtainViewModel(context: Context): ResultViewModel {
        val factory = ViewModelFactory.getInstance(context)
        return ViewModelProvider(this, factory)[ResultViewModel::class.java]
    }

    companion object{
        const val EXTRA_KEYWORD = "keyword"
    }
}