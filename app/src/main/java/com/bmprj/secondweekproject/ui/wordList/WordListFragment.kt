package com.bmprj.secondweekproject.ui.wordList

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bmprj.secondweekproject.R
import com.bmprj.secondweekproject.base.BaseFragment
import com.bmprj.secondweekproject.databinding.FragmentWordListBinding
import com.bmprj.secondweekproject.model.Word
import com.bmprj.secondweekproject.ui.WordAdapter
import com.bmprj.secondweekproject.util.Difficulty
import com.bmprj.secondweekproject.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WordListFragment : BaseFragment<FragmentWordListBinding>(FragmentWordListBinding::inflate) {
    private val wordAdapter by lazy { WordAdapter(::cardClicked) }
    private val viewModel by viewModels<WordListViewModel>()

    override fun setupViews() {
        setupAdapter()
        viewModel.gelAllWords()
        setupLiveDataObserver()
    }

    private fun setupAdapter() {
        with(binding){
            wordListRecy.layoutManager= StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            wordListRecy.adapter=wordAdapter
        }
    }

    private fun cardClicked(id:Int){
        val action = WordListFragmentDirections.actionWordListFragmentToDetailFragment(id, getString(R.string.word))
        findNavController().navigate(action)
    }

    private fun setupLiveDataObserver(){
        lifecycleScope.launchWhenStarted {
            viewModel._words.collect{ state ->
                when(state){
                    is UiState.Success ->{
                        wordAdapter.updateList(state.result)
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
}