package com.bmprj.secondweekproject.ui.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bmprj.secondweekproject.base.BaseFragment
import com.bmprj.secondweekproject.databinding.FragmentSplashBinding
import com.bmprj.secondweekproject.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel by viewModels<SplashViewModel>()

    override fun setupViews() {
        viewModel.getJson()
        setupLiveDataObserver()
    }

    private fun setupLiveDataObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel._insert.collect{ state ->
                when(state){
                    is UiState.Success ->{
                        val action = SplashFragmentDirections.actionSplashFragmentToWordListFragment()
                        findNavController().navigate(action)
                    }

                    is UiState.Error -> {
                        println(state.error.message)
                    }
                    UiState.Loading -> {
                        println("loadinggggg")
                    }
                }

            }
        }
    }

}