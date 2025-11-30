package com.app.gameshelf.ui.screens.gameDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gameshelf.data.model.Achievement
import com.app.gameshelf.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AchievementsUiState(
    val achievements: List<Achievement> = emptyList(),
    val unlockedAchievements: List<Achievement> = emptyList(),
    val lockedAchievements: List<Achievement> = emptyList(),
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
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = repository.getAchievements(gameId, playerId)

            result.onSuccess { achievements ->
                _uiState.update {
                    it.copy(
                        achievements = achievements,
                        unlockedAchievements = achievements.filter { it.achieved },
                        lockedAchievements = achievements.filter { !it.achieved },
                        isLoading = false
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = exception.message ?: "Erro desconhecido"
                    )
                }
            }
        }
    }
}