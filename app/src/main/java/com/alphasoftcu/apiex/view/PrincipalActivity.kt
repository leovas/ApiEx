package com.alphasoftcu.apiex.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.alphasoftcu.apiex.databinding.ActivityPrincipalBinding
import com.alphasoftcu.apiex.view.adapters.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

//Constant identifier for data on Intent for start PostDetailsActivity
const val POST_ID = "post_id"

/**
 * Main activity of the App. Hold Tabs and ViewPager that manage Fragment
 * used in the UI
 */
class PrincipalActivity : AppCompatActivity() {


    private lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}