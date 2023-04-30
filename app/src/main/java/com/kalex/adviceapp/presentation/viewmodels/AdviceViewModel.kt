package com.kalex.adviceapp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalex.adviceapp.common.AdviceState
import com.kalex.adviceapp.common.Resource
import com.kalex.adviceapp.model.usecase.AdviceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdviceViewModel @Inject constructor(
    private val adviceUseCase: AdviceUseCase,
) : ViewModel() {

    private val _advice = mutableStateOf(AdviceState())
    var advice: State<AdviceState> = _advice

    fun getAdvice() {
        adviceUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _advice.value = AdviceState(Advice = result.data?.slip?.advice ?: "no advice")
                }

                is Resource.Loading -> {
                    _advice.value = AdviceState(isLoading = true)
                }

                is Resource.Error -> {
                    _advice.value = AdviceState(isError = true, Error = result.message ?: "ERROR")
                }
            }
        }.launchIn(viewModelScope)
    }
}
