package com.app.gameshelf.ui.screens.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.app.gameshelf.data.model.GameDataApi
import com.app.gameshelf.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameDetailsUiState(
    val gameData: GameDataApi? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class GameDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GameRepository(application.applicationContext)

    private val _uiState = MutableStateFlow(GameDetailsUiState())
    val uiState: StateFlow<GameDetailsUiState> = _uiState.asStateFlow()

    fun loadGameDetails(gameId: String, steamId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = repository.getGameDetails(gameId, steamId)

            result.onSuccess { gameData ->
                _uiState.update {
                    it.copy(
                        gameData = gameData,
                        isLoading = false
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = exception.message ?: "Error unknown"
                    )
                }
            }
        }
    }
}