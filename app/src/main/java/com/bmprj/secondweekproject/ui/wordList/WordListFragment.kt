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

    val array = ArrayList<Word>()
    val model = Word(word = "Difficulty", translate = "zorlu", difficulty = Difficulty.HARD, pronounce = "eejej",imgResId = R.drawable.icon_back)
    val model1 = Word(word = "Difficulasdasdasdasdassdfsadfsadfsadfsadfsadfsadfsadfsdfdasdasdty", translate = "zorlu", difficulty = Difficulty.MEDIUM,pronounce = "eejej", imgResId = R.drawable.icon_back)
    val model2 = Word(word = "Difficultyaa", translate = "zorlu", difficulty = Difficulty.EASY, pronounce = "eejej",imgResId = R.drawable.icon_back)
    val model3 = Word(word = "Diffffffficulasdasdasdasdassdfsadfsadfsadfsadfsadfsadfsadfsdfdasdasdty", translate = "zorlu", difficulty = Difficulty.MEDIUM,pronounce = "eejej", imgResId = R.drawable.icon_back)
    override fun setupViews() {

        array.add(model)
        array.add(model1)
        array.add(model2)
        array.add(model3)

        viewModel.insertAllWords(array)
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
        val action = WordListFragmentDirections.actionWordListFragmentToDetailFragment(id)
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