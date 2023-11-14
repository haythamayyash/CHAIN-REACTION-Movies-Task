package com.chainreaction.task.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chainreaction.task.domain.model.Movie
import com.chainreaction.task.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel() {

    private val _eventState = MutableSharedFlow<MoviesEvent>(replay = 0)
    val eventState: SharedFlow<MoviesEvent> = _eventState

    fun getMovies(): Flow<PagingData<Movie>> {
        return getMoviesUseCase().cachedIn(viewModelScope)
    }

    fun onShareClicked(title: String? , overview: String?) = viewModelScope.launch{
        _eventState.emit(MoviesEvent.Share(title, overview))
    }

    sealed class MoviesEvent {
        data class Share(val title: String?, val overview: String?) : MoviesEvent()
    }

}
