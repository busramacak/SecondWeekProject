package com.bmprj.secondweekproject.ui.detail

import androidx.navigation.fragment.navArgs
import com.bmprj.secondweekproject.base.BaseFragment
import com.bmprj.secondweekproject.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val bundle: DetailFragmentArgs by navArgs()
    private val wordId:Int by lazy { bundle.wordId }
    override fun setupViews() {
        println("id : $wordId")
    }

}