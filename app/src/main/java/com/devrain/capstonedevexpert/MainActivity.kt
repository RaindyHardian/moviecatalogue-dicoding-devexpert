package com.devrain.capstonedevexpert

import android.graphics.Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.devrain.capstonedevexpert.databinding.ActivityMainBinding
import com.devrain.capstonedevexpert.favorite.FavoriteFragment
import com.devrain.capstonedevexpert.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, HomeFragment())
                .commit()
            supportActionBar?.title = "Movie App"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                val mFavoriteFragment = FavoriteFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, mFavoriteFragment)
                    .addToBackStack(null)
                    .commit()
            }
            else -> return true
        }
        return true
    }
}