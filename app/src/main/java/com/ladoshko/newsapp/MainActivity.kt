package com.ladoshko.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ladoshko.newsapp.databinding.ActivityMainBinding
import com.ladoshko.newsapp.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //a24cb1138dcd4cb5b655db70292967d3
    private var _binding : ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.fragment_splash)
        Handler(Looper.myLooper()!!).postDelayed({
            setContentView(mBinding.root)
            bottom_nav_menu.setupWithNavController(navController = nav_host_fragment.findNavController())
        }, 5000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}