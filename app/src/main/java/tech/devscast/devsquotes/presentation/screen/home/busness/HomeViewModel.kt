package tech.devscast.devsquotes.presentation.screen.home.busness

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.devscast.devsquotes.data.repository.QuotesRepository
import tech.devscast.devsquotes.service.workmanager.NotificationWorkManager
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val application: Application, private val quotesRepository: QuotesRepository) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)


    private val _data = MutableStateFlow<HomeState>(HomeState.Uninitialized)
    val data: StateFlow<HomeState>
        get() = _data

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                quotesRepository.refresh()
            }
            _data.emit(HomeState.Loading)
            try {
                quotesRepository.getQuotes().collect { _data.emit(HomeState.Success(it)) }
            } catch (t: Throwable) {
                _data.emit(HomeState.Error(t.message.toString()))
            }
        }
    }

    internal fun applyBlur(blurLevel: Int) {
        workManager.enqueue(OneTimeWorkRequest.from(NotificationWorkManager::class.java))
    }
}
