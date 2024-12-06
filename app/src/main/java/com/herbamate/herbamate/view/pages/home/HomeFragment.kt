package com.herbamate.herbamate.view.pages.home

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.herbamate.herbamate.R
import com.herbamate.herbamate.databinding.FragmentHomeBinding
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
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

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


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}