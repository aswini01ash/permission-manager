package com.example.permissionmanager

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.permissionmanager.adapter.ViewPagerAdapter
import com.example.permissionmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupViewPager()
        setupTabClicks()
    }

    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter

        // Handle page changes
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateTabAppearance(position)
            }
        })
    }

    private fun setupTabClicks() {
        binding.tabProfile.setOnClickListener {
            binding.viewPager.currentItem = 0
        }

        binding.tabPermission.setOnClickListener {
            binding.viewPager.currentItem = 1
        }

        binding.tabEmail.setOnClickListener {
            binding.viewPager.currentItem = 2
        }
    }

    private fun updateTabAppearance(selectedPosition: Int) {

        resetTabAppearance(binding.tabProfile)
        resetTabAppearance(binding.tabPermission)
        resetTabAppearance(binding.tabEmail)

        when (selectedPosition) {
            0 -> {
                setSelectedTabAppearance(binding.tabProfile)
                binding.tvTitle.text = getString(R.string.profile)
            }
            1 -> {
                setSelectedTabAppearance(binding.tabPermission)
                binding.tvTitle.text = getString(R.string.permission)
            }
            2 -> {
                setSelectedTabAppearance(binding.tabEmail)
                binding.tvTitle.text = getString(R.string.email)
            }
        }
    }


    private fun resetTabAppearance(tab: android.widget.TextView) {
        tab.apply {
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))
            setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.black))
            typeface = Typeface.DEFAULT
        }
    }

    private fun setSelectedTabAppearance(tab: android.widget.TextView) {
        tab.apply {
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.purple_light))
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.purple))
            typeface = Typeface.DEFAULT_BOLD
        }
    }
}