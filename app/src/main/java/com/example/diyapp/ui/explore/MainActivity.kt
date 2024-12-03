package com.example.diyapp.ui.explore

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.diyapp.R
import com.example.diyapp.data.adapter.user.SessionManager
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
                R.id.newPublicationFragment -> navigateWithLoginCheck(
                    R.id.newPublicationFragment,
                    R.id.loginFragment
                )

                R.id.myPublicationsFragment -> navigateWithLoginCheck(
                    R.id.myPublicationsFragment,
                    R.id.loginFragment
                )

                R.id.favoritesFragment -> navigateWithLoginCheck(
                    R.id.favoritesFragment,
                    R.id.loginFragment
                )

                R.id.loginFragment -> navigateWithLoginCheck(
                    R.id.manageAccountsFragment,
                    R.id.loginFragment
                )
            }
            true
        }
    }

    private fun navigateWithLoginCheck(destinationIfLoggedIn: Int, destinationIfNotLoggedIn: Int) {
        if (SessionManager.isUserLoggedIn(this)) {
            navController.navigate(destinationIfLoggedIn)
        } else {
            navController.navigate(destinationIfNotLoggedIn)
            Toast.makeText(this, getString(R.string.loginFirst), Toast.LENGTH_SHORT).show()
        }
    }
}