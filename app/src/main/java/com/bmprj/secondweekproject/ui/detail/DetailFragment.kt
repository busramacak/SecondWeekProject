package com.bmprj.secondweekproject.ui.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bmprj.secondweekproject.R
import com.bmprj.secondweekproject.base.BaseFragment
import com.bmprj.secondweekproject.databinding.FragmentDetailBinding
import com.bmprj.secondweekproject.util.getDrawableIdFromName
import com.bmprj.secondweekproject.util.toastLong
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel by viewModels<DetailViewModel>()
    private val bundle: DetailFragmentArgs by navArgs()
    private val wordId: Int by lazy { bundle.wordId }

    override fun setupViews() {
        viewModel.getDetail(wordId)
        setupLiveDataObserver()
        setupListeners()
    }

    private fun setupLiveDataObserver() {
        viewModel._detailWord.handleState(
            onLoading = {},
            onError = {
                toastLong(it.message.toString())
            },
            onSuccess = {
                with(binding) {
                    wordText.text = it.word
                    pronounce.text = it.pronounce
                    pronounceFr.text = it.pronounceFr
                    translateTr.text = it.translateTr
                    translateFr.text = it.translateFr
                    imgg.setImageResource(getDrawableIdFromName(requireContext(), it.imgResId))
                    sentence.text = it.sentence
                    sentenceTr.text = it.sentenceTr
                    sentenceFr.text = it.sentenceFr
                    learnButton.text = if (it.isLearned) {
                        getString(R.string.unLearned)
                    } else {
                        getString(R.string.learned)
                    }
                }
            }
        )

        viewModel._setLearned.handleState(
            onLoading = {},
            onError = {
                toastLong(it.message.toString())
            },
            onSuccess = {
                binding.learnButton.text =
                    if (binding.learnButton.text == getString(R.string.learned)) {
                        getString(R.string.unLearned)
                    } else {
                        getString(R.string.learned)
                    }
            }
        )
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
        findNavController().navigateUp()
    }
}