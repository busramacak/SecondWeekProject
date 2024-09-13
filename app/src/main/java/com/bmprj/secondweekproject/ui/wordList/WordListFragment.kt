package com.bmprj.secondweekproject.ui.wordList

import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bmprj.secondweekproject.R
import com.bmprj.secondweekproject.base.BaseFragment
import com.bmprj.secondweekproject.databinding.AddWordBottomLayoutBinding
import com.bmprj.secondweekproject.databinding.FragmentWordListBinding
import com.bmprj.secondweekproject.model.Word
import com.bmprj.secondweekproject.ui.WordAdapter
import com.bmprj.secondweekproject.util.Difficulty
import com.bmprj.secondweekproject.util.setVisibility
import com.bmprj.secondweekproject.util.toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
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
            binding.swipeRefresh.isRefreshing = false
        }

        binding.fab.setOnClickListener { fabClicked() }
    }

    private fun fabClicked() {
        val addWordLayoutBinding =
            AddWordBottomLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        val bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setContentView(addWordLayoutBinding.root)

        with(addWordLayoutBinding) {
            confirmBtn.setOnClickListener {
                val word = wordEdt.text.toString()
                val translateTr = translateTrEdt.text.toString()
                val translateFr = if(translateFrEdt.text.isEmpty()){ "-"
                }else{ translateFrEdt.text.toString() }
                val pronounce = pronounceEdt.text.toString()
                val pronounceFr = if (pronounceFrEdt.text.isEmpty()) {
                    "-"
                } else {
                    pronounceFrEdt.text.toString()
                }
                val sentence = sentenceEdt.text.toString()
                val sentenceFr = if (sentenceFrEdt.text.isEmpty()) {
                    "-"
                } else {
                    sentenceFrEdt.text.toString()
                }
                val newWordImg = "icon_new_word"
                val sentenceTr = sentenceTrEdt.text.toString()
                val rating = ratingBar.rating
                val difficulty = when (rating) {
                    1.0F -> Difficulty.EASY
                    2.0F -> Difficulty.MEDIUM
                    else -> Difficulty.HARD
                }

                val wordModel = Word(
                    word = word,
                    translateTr = translateTr,
                    translateFr = translateFr,
                    pronounce = pronounce,
                    pronounceFr = pronounceFr,
                    difficulty = difficulty,
                    imgResId = newWordImg,
                    sentence = sentence,
                    sentenceTr = sentenceTr,
                    sentenceFr = sentenceFr
                )

                if (word.isEmpty() || translateTr.isEmpty() || pronounce.isEmpty() ||
                    sentence.isEmpty() || sentenceTr.isEmpty() || rating == 0f
                ) {
                    toast(getString(R.string.spaceFillPls))
                } else {
                    addNewWord(wordModel)
                    bottomSheet.dismiss()
                }
            }
            cancelBtn.setOnClickListener { bottomSheet.dismiss() }
            bottomSheet.show()
        }

    }

    private fun addNewWord(wordModel: Word) {
        viewModel.addNewWord(wordModel)
    }

    private fun setupAdapter() {
        with(binding) {
            wordListRecy.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            wordListRecy.adapter = wordAdapter
            searchRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            searchRecyclerView.adapter = searchListAdapter
        }
    }

    private fun cardClicked(id: Int) {
        val action = WordListFragmentDirections.actionWordListFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }

    private fun setupLiveDataObserver() {

        viewModel._words.handleState(
            onLoading = {},
            onError = {
                toast(it.message.toString())
            },
            onSuccess = {
                wordAdapter.updateList(it)
                searchListAdapter.updateList(it)
            }
        )

        lifecycleScope.launch {
            viewModel._filteredWords.handleState(
                onLoading = {},
                onError = {
                    toast(it.message.toString())
                },
                onSuccess = {
                    binding.searchText.setVisibility(it.isEmpty())
                    searchListAdapter.updateList(it)
                }
            )
        }
        viewModel._isNewWordAdd.handleState(
            onLoading = {},
            onError = {
                toast(getString(R.string.somethingWrong))
            },
            onSuccess = {
                viewModel.gelAllWords()
            }
        )
    }
}