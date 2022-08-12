package tech.devscast.devsquotes.presentation.screen.favorites.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.data.repository.QuotesRepository
import tech.devscast.devsquotes.presentation.screen.favorites.business.FavoriteState.*
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository
) : ViewModel() {

    val quotesState: StateFlow<FavoriteState> = quotesRepository.getFavorites().map { quotes ->
        if (quotes.isNotEmpty()) {
            Success(quotes = quotes)
        } else {
            Empty
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Loading
    )

    fun removeFromFavorites(quote: Quote) {
        viewModelScope.launch(Dispatchers.IO) {
            quotesRepository.removeFromFavorites(quote)
        }
    }

}