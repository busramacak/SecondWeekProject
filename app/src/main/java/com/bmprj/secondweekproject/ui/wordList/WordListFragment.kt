package com.bmprj.secondweekproject.ui.wordList

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bmprj.secondweekproject.R
import com.bmprj.secondweekproject.base.BaseFragment
import com.bmprj.secondweekproject.databinding.FragmentWordListBinding
import com.bmprj.secondweekproject.model.Word
import com.bmprj.secondweekproject.ui.WordAdapter
import com.bmprj.secondweekproject.util.Difficulty

class WordListFragment : BaseFragment<FragmentWordListBinding>(FragmentWordListBinding::inflate) {
    private val wordAdapter by lazy { WordAdapter(::cardClicked) }
    val array = ArrayList<Word>()
    val model = Word(word = "Difficulty", translate = "zorlu", difficulty = Difficulty.HARD, pronounce = "eejej",imgResId = R.drawable.icon_back)
    val model1 = Word(word = "Difficulasdasdasdasdassdfsadfsadfsadfsadfsadfsadfsadfsdfdasdasdty", translate = "zorlu", difficulty = Difficulty.MEDIUM,pronounce = "eejej", imgResId = R.drawable.icon_back)
    val model2 = Word(word = "Difficulty", translate = "zorlu", difficulty = Difficulty.EASY, pronounce = "eejej",imgResId = R.drawable.icon_back)
    val model3 = Word(word = "Difficulasdasdasdasdassdfsadfsadfsadfsadfsadfsadfsadfsdfdasdasdty", translate = "zorlu", difficulty = Difficulty.MEDIUM,pronounce = "eejej", imgResId = R.drawable.icon_back)
    override fun setupViews() {
        setupAdapter()
        array.add(model)
        array.add(model1)
        array.add(model2)
        array.add(model3)
        wordAdapter.updateList(array)
        println(array)
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
}