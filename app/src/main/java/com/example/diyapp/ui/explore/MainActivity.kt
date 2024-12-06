package com.example.diyapp.ui.explore

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.databinding.ActivityMainBinding
import com.example.diyapp.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        setupObservers()
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
        val isLoggedIn = SessionManager.isUserLoggedIn(this)
        viewModel.checkLoginAndNavigate(isLoggedIn, destinationIfLoggedIn, destinationIfNotLoggedIn)
    }

    private fun setupObservers() {
        viewModel.navigationCommand.observe(this) { (destination, showToast) ->
            if (showToast) {
                SessionManager.showToast(this, R.string.loginFirst)
            }
            navController.navigate(destination)
        }
    }
}