package tech.devscast.devsquotes.presentation.screen.showquote.business

import tech.devscast.devsquotes.data.model.Quote

sealed class ShowQuoteState {
    object Initial : ShowQuoteState()
    object Loading : ShowQuoteState()
    data class Error(val message: String) : ShowQuoteState()
    data class Success(val quote: Quote) : ShowQuoteState()
    object Empty : ShowQuoteState()
}
