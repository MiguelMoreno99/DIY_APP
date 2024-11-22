package com.example.diyapp.ui.explore

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.diyapp.R
import com.example.diyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initNavigation()
    }

    private fun initNavigation() {
        val navHost: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.exploreFragment -> navController.navigate(R.id.exploreFragment)
                R.id.newPublicationFragment -> navController.navigate(R.id.newPublicationFragment)
                R.id.myPublicationsFragment -> navController.navigate(R.id.myPublicationsFragment)
                R.id.favoritesFragment -> navController.navigate(R.id.favoritesFragment)
                R.id.loginFragment -> {
                    if (isUserLoggedIn()) {
                        navController.navigate(R.id.manageAccountsFragment)
                    } else {
                        navController.navigate(R.id.loginFragment)
                    }
                }
            }
            true
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPref = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isLoggedIn", false)
    }

    fun setUserLoggedIn(
        loggedIn: Boolean,
        email: String,
        name: String,
        lastname: String,
        photo: String
    ) {
        val sharedPref = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("email", email)
            putString("name", name)
            putString("lastname", lastname)
            putString("photo", photo)
            putBoolean("isLoggedIn", loggedIn)
            apply()
        }
    }
}