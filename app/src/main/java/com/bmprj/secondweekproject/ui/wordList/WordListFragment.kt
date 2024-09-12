package com.bmprj.secondweekproject.ui.wordList

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WordListFragment : BaseFragment<FragmentWordListBinding>(FragmentWordListBinding::inflate) {
    private val wordAdapter by lazy { WordAdapter(::cardClicked) }
    private val searchListAdapter by lazy { WordAdapter(::cardClicked) }
    private val viewModel by viewModels<WordListViewModel>()

    override fun setupViews() {
        setupAdapter()
        viewModel.gelAllWords()
        setupLiveDataObserver()
        setupSearchView()
        setupListeners()
    }

    private fun setupSearchView() {
        with(binding) {
            searchView.setupWithSearchBar(binding.searchBar)
            searchView.editText.addTextChangedListener {
                viewModel.getDataForQuery(it.toString())
            }
        }
    }

    private fun setupListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
            binding.swipeRefresh.isRefreshing=false
        }
    }

    private fun setupAdapter() {
        with(binding){
            wordListRecy.layoutManager= StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            wordListRecy.adapter=wordAdapter
            searchRecyclerView.layoutManager =StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            searchRecyclerView.adapter = searchListAdapter
        }
    }

    private fun cardClicked(id:Int){
        val action = WordListFragmentDirections.actionWordListFragmentToDetailFragment(id, getString(R.string.word))
        findNavController().navigate(action)
    }

    private fun setupLiveDataObserver(){
        lifecycleScope.launch {
            viewModel._words.collect{ state ->
                when(state){
                    is UiState.Success ->{
                        wordAdapter.updateList(state.result)
                        searchListAdapter.updateList(state.result)
                    }

                    is UiState.Error -> {
                        println(state.error.message)
                    }
                    UiState.Loading -> {

                    }
                }

            }
        }

        lifecycleScope.launch {
            viewModel.filteredCoins.collect {
                searchListAdapter.updateList(it)
            }
        }

    }

}