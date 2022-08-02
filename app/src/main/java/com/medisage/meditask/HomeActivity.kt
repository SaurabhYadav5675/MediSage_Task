package com.medisage.meditask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.medisage.meditask.api.ApiService
import com.medisage.meditask.api.RetrofitHelper
import com.medisage.meditask.appDatabase.UserDatabase
import com.medisage.meditask.databinding.ActivityHomeBinding
import com.medisage.meditask.fragment.FavouriteFragment
import com.medisage.meditask.fragment.FragmentAdapter
import com.medisage.meditask.fragment.PostFragment
import com.medisage.meditask.repository.PostRepository

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    lateinit var repository: PostRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val database = UserDatabase.getDatabase(this)
        repository = PostRepository(productService, database, this)

        var viewPager = binding.homeViewPager
        var tabLayout = binding.homeTabs

        val fragmentAdapter = FragmentAdapter(supportFragmentManager)
        fragmentAdapter.addFragment(PostFragment(), "Post")
        fragmentAdapter.addFragment(FavouriteFragment(), "Favourite")

        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

    }
}