package com.app.gameshelf.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

data class GameData(
    val id: String,
    val name: String,
    val imageUrl: String,
)

sealed class SearchUiState {
    object Loading : SearchUiState()
    data class Success(val games: List<GameData>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}

class SearchViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val gamesCache = mutableMapOf<String, GameData>()

    init {
        loadGames()
    }

    fun loadGames() {
        val gameIds = listOf(
            "2161700", "730", "570", "578080", "271590", "1172470",
            "1091500", "892970", "1085660", "1245620", "230410",
            "346110", "2050650", "2747770"
        )

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            try {
                val games = gameIds.map { gameId ->
                    async {
                        gamesCache[gameId] ?: fetchGameData(gameId).also {
                            gamesCache[gameId] = it
                        }
                    }
                }.awaitAll()

                _uiState.value = SearchUiState.Success(games)

            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(
                    e.message ?: "Erro ao carregar jogos"
                )
            }
        }
    }

    private suspend fun fetchGameData(gameId: String): GameData {
        return GameData(
            id = gameId,
            name = "Game $gameId",
            imageUrl = "https://cdn.cloudflare.steamstatic.com/steam/apps/$gameId/library_600x900.jpg"
        )
    }

    fun retry() {
        loadGames()
    }
}
