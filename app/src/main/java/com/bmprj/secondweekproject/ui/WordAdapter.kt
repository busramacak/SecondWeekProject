package com.bmprj.secondweekproject.ui

import com.bmprj.secondweekproject.base.BaseAdapter
import com.bmprj.secondweekproject.databinding.WordListLayoutBinding
import com.bmprj.secondweekproject.model.Word
import com.bmprj.secondweekproject.util.Difficulty
import com.bmprj.secondweekproject.util.setDrawable

class WordAdapter(
    private val cardClicked: (Int) ->Unit
) : BaseAdapter<WordListLayoutBinding,Word>(
    arrayListOf(),
    WordListLayoutBinding::inflate
) {
    override fun bind(binding: WordListLayoutBinding, item: Word) {
        binding.apply {
            wordText.text=item.word
            println("${item.word} +++++++ ${item.isLearned}")

            when(item.difficulty){
                Difficulty.EASY ->{
                    easy.setDrawable(true)
                    medium.setDrawable(false)
                    hard.setDrawable(false)

                }
                Difficulty.MEDIUM -> {
                    easy.setDrawable(true)
                    medium.setDrawable(true)
                    hard.setDrawable(false)
                }
                Difficulty.HARD -> {
                    easy.setDrawable(true)
                    medium.setDrawable(true)
                    hard.setDrawable(true)
                }
            }

            binding.cardV.setOnClickListener {
                cardClicked.invoke(item.id)
            }
        }
    }

}