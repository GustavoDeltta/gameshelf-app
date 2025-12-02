package com.app.gameshelf.ui.screens.gameDetails

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
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

class GameDetailsViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _uiState = MutableStateFlow(GameDetailsUiState())
    val uiState: StateFlow<GameDetailsUiState> = _uiState.asStateFlow()

    fun loadGameDetails(gameId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = repository.getGameDetails(gameId)

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
                        error = exception.message ?: "Erro ao carregar detalhes do jogo"
                    )
                }
            }
        }
    }
}