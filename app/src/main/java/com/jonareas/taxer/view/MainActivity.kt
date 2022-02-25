package com.jonareas.taxer.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jonareas.taxer.R
import com.jonareas.taxer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupNavigation()
        setContentView(binding.root)
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController =navHostFragment.navController

        val topDestinations = setOf(R.id.workerListFragment, R.id.aboutFragment)

        setupActionBarWithNavController(navController,
        configuration = AppBarConfiguration(topDestinations))
        setupBottomNavigation(navController)

    }

    private fun setupBottomNavigation(navController: NavController) =
        binding.bottomNavViewMain.setupWithNavController(navController)

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()


}
