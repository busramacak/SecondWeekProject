package com.bmprj.secondweekproject.ui.learnedList


import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bmprj.secondweekproject.base.BaseFragment
import com.bmprj.secondweekproject.databinding.FragmentLearnedWordsBinding
import com.bmprj.secondweekproject.ui.WordAdapter
import com.bmprj.secondweekproject.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LearnedWordsFragment : BaseFragment<FragmentLearnedWordsBinding>(FragmentLearnedWordsBinding::inflate) {
    private val viewModel by viewModels<LearnedWordsViewModel>()
    private val learnedAdapter by lazy { WordAdapter(::onCardClicked) }

    override fun setupViews() {
        viewModel.getAllLearnedWords()
        setupAdapter()
        setupLiveDataObserver()
    }

    private fun setupLiveDataObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel._learnedWords.collect{ state ->
                when(state){
                    is UiState.Success ->{
                        learnedAdapter.updateList(state.result)
                    }

                    is UiState.Error -> {
                        println(state.error.message)
                    }
                    UiState.Loading -> {

                    }
                }

            }
        }
    }

    private fun setupAdapter(){
        with(binding){
            learnedRecyc.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            learnedRecyc.adapter = learnedAdapter
        }
    }

    private fun onCardClicked(id: Int) {
        val action = LearnedWordsFragmentDirections.actionLearnedWordsFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }
}