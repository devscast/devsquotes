package tech.devscast.devsquotes.presentation.screen.favorites.business

import tech.devscast.devsquotes.data.model.Quote

sealed interface FavoriteState {
    object Empty : FavoriteState
    object Loading : FavoriteState
    data class Success(val quotes: List<Quote>) : FavoriteState
    data class Error(val message: String) : FavoriteState
}