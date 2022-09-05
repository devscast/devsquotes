package tech.devscast.devsquotes.presentation.screen.showquote.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.data.repository.QuotesRepository
import javax.inject.Inject

@HiltViewModel
class ShowQuoteViewModel @Inject constructor(private val quotesRepository: QuotesRepository) :
    ViewModel() {

    private val _quote = MutableStateFlow<ShowQuoteState>(ShowQuoteState.Initial)
    val quote: StateFlow<ShowQuoteState>
        get() = _quote

    fun getQuoteById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _quote.emit(ShowQuoteState.Loading)
            try {
                if (id.isNotEmpty()) {
                    val data = quotesRepository.getQuoteById(id = id)
                    _quote.emit(ShowQuoteState.Success(data))
                } else {
                    quotesRepository.getQuotes().collect {
                        _quote.emit(ShowQuoteState.Success(it.random()))
                    }
                }
            } catch (e: Exception) {
                _quote.emit(ShowQuoteState.Error(e.message.toString()))
            }
        }
    }

    fun addToFavorite(quote: Quote) {
        viewModelScope.launch(Dispatchers.IO) {
            quotesRepository.addToFavorite(quote)
        }
    }
}
