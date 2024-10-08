package com.bmprj.secondweekproject.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.bmprj.secondweekproject.util.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseFragment<VB: ViewBinding>(
    private val bindingInflater:(LayoutInflater) -> VB
) : Fragment() {

    private lateinit var _binding:VB
    protected val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    abstract fun setupViews()

    fun <T> StateFlow<UiState<T>>.handleState(
        coroutineExceptionHandler: CoroutineExceptionHandler?=null,
        onLoading :(() -> Unit)? = null,
        onError : ((Throwable) -> Unit)? = null,
        onSuccess : ((T) -> Unit)? = null
    ){
        lifecycleScope.launch (coroutineExceptionHandler?:EmptyCoroutineContext) {
            this@handleState
                .onStart {
                    onLoading?.invoke()
                }
                .catch {
                    onError?.invoke(it)
                }
                .collect{ state ->
                    when (state) {
                        is UiState.Loading -> {
                            onLoading?.invoke()
                        }

                        is UiState.Success -> {
                            onSuccess?.invoke(state.result)
                        }

                        is UiState.Error -> {
                            onError?.invoke(state.error)
                        }
                    }
                }
        }
    }
}