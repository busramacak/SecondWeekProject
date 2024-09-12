package com.bmprj.secondweekproject.ui.wordList

import android.view.LayoutInflater
import android.view.View
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
import com.bmprj.secondweekproject.util.toast
import com.bmprj.secondweekproject.util.toastLong
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
                val word = wordEdt.editText?.text.toString()
                val translate = translateEdt.editText?.text.toString()
                val pronounce = pronounceEdt.editText?.text.toString()
                val sentence = sentenceEdt.editText?.text.toString()
                val newWordImg = "icon_new_word"
                val sentenceTranslate = sentenceTranslateEdt.editText?.text.toString()
                val rating = ratingBar.rating
                val difficulty = when (rating) {
                    1.0F -> Difficulty.EASY
                    2.0F -> Difficulty.MEDIUM
                    else -> Difficulty.HARD
                }

                val wordModel = Word(
                    word = word,
                    translate = translate,
                    pronounce = pronounce,
                    difficulty = difficulty,
                    imgResId = newWordImg,
                    sentence = sentence,
                    sentenceTranslate = sentenceTranslate
                )

                if (word.isEmpty() || translate.isEmpty() || pronounce.isEmpty() || sentence.isEmpty() || sentenceTranslate.isEmpty() || rating == 0f) {
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
                toastLong(it.message.toString())
            },
            onSuccess = {
                wordAdapter.updateList(it)
                searchListAdapter.updateList(it)
            }
        )



        lifecycleScope.launch {
            viewModel._filteredCoins.collect {
                if (it.isEmpty()) {
                    binding.searchText.visibility = View.VISIBLE
                } else {
                    binding.searchText.visibility = View.INVISIBLE
                }
                searchListAdapter.updateList(it)
            }
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