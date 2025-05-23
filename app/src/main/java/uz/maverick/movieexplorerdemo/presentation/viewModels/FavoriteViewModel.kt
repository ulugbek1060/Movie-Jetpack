package uz.maverick.movieexplorerdemo.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity
import uz.maverick.movieexplorerdemo.domain.repositories.SavedMoviesRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: SavedMoviesRepository
) : ViewModel() {

    private val _moviesState = MutableStateFlow<List<MovieItemEntity>>(emptyList())
    val moviesState: StateFlow<List<MovieItemEntity>> = _moviesState

    init {
        viewModelScope.launch {
            repository.getFavoriteMovies()
                .collect { movies ->
                    _moviesState.value = movies
                }
        }
    }

    fun clearAllFavorites() {
        viewModelScope.launch {
            repository.clearAllFavorites()
        }
    }

}



