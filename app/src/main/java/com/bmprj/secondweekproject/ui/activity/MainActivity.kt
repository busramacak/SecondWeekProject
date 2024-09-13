package com.bmprj.secondweekproject.ui.activity

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bmprj.secondweekproject.R
import com.bmprj.secondweekproject.databinding.ActivityMainBinding
import com.bmprj.secondweekproject.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding
    private var resultt = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getJson()
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupPreDrawListener()
        lifecycleScope.launch {
            setupLiveDataObserver()
        }

    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navHostFragment.navController
        )

        navHostFragment.navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.wordListFragment || nd.id == R.id.learnedWordsFragment) {
                showBottomNav()
            } else {
                hideBottomNav()
            }
        }
    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun setupPreDrawListener() {
        val content: View = findViewById(android.R.id.content)

        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    val isReady = resultt

                    return if (isReady) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }

    private suspend fun setupLiveDataObserver() {
        val result = CompletableDeferred<Boolean>()
        lifecycleScope.launch {
            viewModel._insert.collect { state ->
                when (state) {
                    is UiState.Success -> {
                        result.complete(true)
                    }

                    is UiState.Error -> {
                        result.complete(false)
                    }

                    UiState.Loading -> {}
                }
            }
        }
        resultt = result.await()
    }
}