package com.bmprj.secondweekproject.ui.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bmprj.secondweekproject.R
import com.bmprj.secondweekproject.base.BaseFragment
import com.bmprj.secondweekproject.databinding.FragmentDetailBinding
import com.bmprj.secondweekproject.util.UiState
import com.bmprj.secondweekproject.util.getDrawableIdFromName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel by viewModels<DetailViewModel>()
    private val bundle: DetailFragmentArgs by navArgs()
    private val wordId: Int by lazy { bundle.wordId }
    private val back: String by lazy { bundle.back }

    override fun setupViews() {
        viewModel.getDetail(wordId)
        setupLiveDataObserver()
        setupListeners()
    }

    private fun setupLiveDataObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel._detailWord.collect { state ->
                when (state) {
                    is UiState.Success -> {
                        with(binding) {
                            wordText.text = state.result.word
                            wordPronounce.text = state.result.pronounce
                            wordTranslate.text = state.result.translate
                            imgg.setImageResource(getDrawableIdFromName(requireContext(),state.result.imgResId))
                            sentence.text = state.result.sentence
                            sentenceTranslate.text = state.result.sentenceTranslate
                            learnButton.text = if (state.result.isLearned) {
                                getString(R.string.unLearned)
                            } else {
                                getString(R.string.learned)
                            }
                        }
                    }

                    is UiState.Error -> {
                        println(state.error.message)
                    }

                    UiState.Loading -> {

                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel._setLearned.collect { state ->
                when (state) {
                    is UiState.Success -> {
                        with(binding) {
                            learnButton.text =
                                if (learnButton.text == getString(R.string.learned)) {
                                    getString(R.string.unLearned)
                                } else {
                                    getString(R.string.learned)
                                }
                        }
                    }

                    is UiState.Error -> {
                        println(state.error.message)
                    }

                    UiState.Loading -> {
                        println("loading learn button click")
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        with(binding) {
            backButton.setOnClickListener { backButtonClicked() }
            learnButton.setOnClickListener { learnButtonClicked() }
        }
    }

    private fun learnButtonClicked() {
        val isLearned = if (binding.learnButton.text == getString(R.string.learned)) {
            1
        } else {
            0
        }

        viewModel.setLearned(wordId, isLearned)
    }

    private fun backButtonClicked() {
//        DetailFragmentDirections.actionDetailFragmentToLearnedWordsFragment()
//    } else {
//        DetailFragmentDirections.actionDetailFragmentToWordListFragment()
//    }
//
//    findNavController().navigate(action) val action = if (back == getString(R.string.learned)) {


        findNavController().navigateUp()
    }


}