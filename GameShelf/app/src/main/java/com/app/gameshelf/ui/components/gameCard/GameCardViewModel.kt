package com.app.gameshelf.ui.components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GameCardState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false
)

class GameCardViewModel : ViewModel() {
    private val _state = MutableStateFlow(GameCardState())
    val state: StateFlow<GameCardState> = _state.asStateFlow()

    fun onImageLoaded() {
        _state.value = _state.value.copy(isLoading = false)
    }

    fun onImageError() {
        _state.value = _state.value.copy(isLoading = false, hasError = true)
    }
}