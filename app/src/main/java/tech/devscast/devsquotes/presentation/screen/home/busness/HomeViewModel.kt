package tech.devscast.devsquotes.presentation.screen.home.busness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.data.repository.QuotesRepository
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val quotesRepository: QuotesRepository) :
    ViewModel() {

    val quotesState: StateFlow<HomeState> = quotesRepository.getQuotes().map { quotes ->
        try {
            if (quotes.isNotEmpty()) {
                HomeState.Success(quotes)
            } else {
                HomeState.Empty
            }
        } catch (e: Exception) {
            HomeState.Error(e.message.toString())
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = HomeState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    init {

        viewModelScope.launch(Dispatchers.IO) {
            quotesRepository.refresh()
        }
    }

    fun addToFavorite(quote: Quote) {
        viewModelScope.launch(Dispatchers.IO) {
            quotesRepository.addToFavorite(quote)
        }
    }
}
