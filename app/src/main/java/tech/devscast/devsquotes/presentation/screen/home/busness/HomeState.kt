package tech.devscast.devsquotes.presentation.screen.home.busness

import tech.devscast.devsquotes.data.model.Quote

sealed class HomeState {
    object Loading : HomeState()
    data class Error(val message: String) : HomeState()
    data class Success(val quotes: List<Quote>) : HomeState()
    object Empty : HomeState()
}
