package uz.maverick.movieexplorerdemo.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity
import uz.maverick.movieexplorerdemo.domain.repositories.MoviesRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    val movies = moviesRepository.getPopularMovies().cachedIn(viewModelScope)

    private val _searchMovies = MutableLiveData<PagingData<MovieItemEntity>>()
    val searchMovies: LiveData<PagingData<MovieItemEntity>> = _searchMovies

    var job: Job? = null;

    fun searchMovies(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            moviesRepository.searchMovies(query)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _searchMovies.value = pagingData
                }
        }
    }

}