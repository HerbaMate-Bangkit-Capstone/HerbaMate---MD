package com.herbamate.herbamate.view.pages.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.herbamate.herbamate.R
import com.herbamate.herbamate.databinding.FragmentHomeBinding
import com.herbamate.herbamate.utils.Result
import com.herbamate.herbamate.utils.factory.ViewModelFactory
import com.herbamate.herbamate.view.pages.detail.DetailActivity
import com.herbamate.herbamate.view.pages.result.ResultActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = obtainViewModel(requireActivity())

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (savedInstanceState === null){
            viewModel.getAllHerb()
        }

        binding.rvHomeHerbs.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvHomeHerbs.isNestedScrollingEnabled = false



        binding.cardFeature.setOnClickListener {
            val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
            if (bottomNavigationView != null) {
                bottomNavigationView.selectedItemId = R.id.navigation_recommendation
            }
        }

        binding.homeSearchText.setOnKeyListener { view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_UP){
                val intent = Intent(activity?.baseContext, ResultActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_KEYWORD, binding.homeSearchText.text.toString())
                startActivity(intent)
                true
            }
            false
        }

        viewModel.herb.observe(viewLifecycleOwner){  result ->
            when(result){
                is Result.Error ->{
                    showLoading(false)
                    showErrorDialog(result.error)
                }
                Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    val adapter = HomeAdapter()
                    adapter.setHerbList(result.data)
                    binding.rvHomeHerbs.adapter = adapter
                }
            }
        }


        binding.rvHomeHerbs

        return root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressHome.visibility = if (isLoading) View.VISIBLE else View.GONE
    }



    private fun showErrorDialog(message: String, onFinish : () -> Unit = { activity?.finish()  }) {
        AlertDialog.Builder(requireActivity().baseContext).apply {
            setTitle(resources.getString(R.string.error))
            setMessage(message)
            setPositiveButton(resources.getString(R.string.okay)) { _, _ ->
                onFinish()
            }
            create()
            show()
        }
    }

    private fun obtainViewModel(context: Context): HomeViewModel {
        val factory = ViewModelFactory.getInstance(context)
        return ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}