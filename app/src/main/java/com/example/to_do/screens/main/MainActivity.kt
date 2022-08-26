package com.example.to_do.screens.main

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.to_do.R
import com.example.to_do.databinding.ActivityMainBinding
import com.example.to_do.screens.detail.SecondFragment

private const val SAVED_COLOR_KEY = "saved_color"
class MainActivity : AppCompatActivity(), SecondFragment.VisibleFab {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_FirstFragment_to_SecondFragment)
            it.visibility = View.GONE
        }

        sharedPreferences = getPreferences(MODE_PRIVATE)
        viewModel.colorOfTasks.value = ColorDrawable(sharedPreferences.getInt(SAVED_COLOR_KEY, Color.WHITE))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        fun toSharedPref(color: Int) {
            sharedPreferences.edit().putInt(SAVED_COLOR_KEY, color).apply()
        }
        return when (item.itemId) {
            R.id.default_color -> {
                viewModel.colorOfTasks.value = ColorDrawable(Color.WHITE)
                toSharedPref(Color.WHITE)
                true
            }
            R.id.yellow -> {
                viewModel.colorOfTasks.value = ColorDrawable(Color.YELLOW)
                toSharedPref(Color.YELLOW)
                true
            }
            R.id.blue -> {
                viewModel.colorOfTasks.value = ColorDrawable(Color.BLUE)
                toSharedPref(Color.BLUE)
                true
            }
            R.id.green -> {
                viewModel.colorOfTasks.value = ColorDrawable(Color.GREEN)
                toSharedPref(Color.GREEN)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun makeFabVisible() {
        binding.fab.visibility = View.VISIBLE
    }
}