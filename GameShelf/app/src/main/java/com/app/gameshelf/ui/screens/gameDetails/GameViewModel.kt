package com.app.gameshelf.ui.screens.gameDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gameshelf.data.model.Achievement
import com.app.gameshelf.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AchievementsUiState(
    val achievements: List<Achievement> = emptyList(),
    val gameId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class GameViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _uiState = MutableStateFlow(AchievementsUiState())
    val uiState: StateFlow<AchievementsUiState> = _uiState.asStateFlow()

    fun loadAchievements(gameId: String, playerId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = repository.getAchievements(gameId, playerId)

            result.onSuccess { achievements ->
                _uiState.value = _uiState.value.copy(
                    achievements = achievements,
                    isLoading = false
                )
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Erro desconhecido"
                )
            }
        }
    }
}