package uz.maverick.movieexplorerdemo.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import uz.maverick.movieexplorerdemo.domain.entities.MovieDetailEntity
import uz.maverick.movieexplorerdemo.domain.entities.toMovieItemEntity
import uz.maverick.movieexplorerdemo.domain.repositories.MoviesRepository
import uz.maverick.movieexplorerdemo.domain.repositories.SavedMoviesRepository
import uz.maverick.movieexplorerdemo.utils.Logger
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val savedMoviesRepository: SavedMoviesRepository,
) : ViewModel() {

    private val _movieDetailState = MutableStateFlow<State>(State.Loading)
    val movieDetailState: StateFlow<State> = _movieDetailState

    fun getMovieDetail(movieId: Int) {
        try {
            viewModelScope.launch {
                combine(
                    savedMoviesRepository.isMovieInFavorites(movieId),
                    repository.getMovieDetails(movieId)
                ) { isInFavorite, movie ->
                    State.Success(
                        data = movie,
                        isInFavorite = isInFavorite
                    )
                }.catch {
                    _movieDetailState.value = State.Error(it.message ?: "Unknown error")
                }.collectLatest {
                    _movieDetailState.value = it
                }
            }
        } catch (e: Exception) {
            _movieDetailState.value = State.Error(e.message ?: "Unknown error")
        }
    }

    fun toggleFavoriteButton() {
        viewModelScope.launch {
            if (_movieDetailState.value is State.Loading ||
                _movieDetailState.value is State.Error
            ) {
                return@launch
            }
            // save
            val state = (_movieDetailState.value as State.Success)
            Logger.d("isInFavorite: ${state.isInFavorite}")
            if (state.isInFavorite){
                savedMoviesRepository.removeFromFavorites(state.data.id ?: -1)
            } else {
                savedMoviesRepository.addToFavorites(state.data.toMovieItemEntity())
            }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(
            val data: MovieDetailEntity,
            val isInFavorite: Boolean = false
        ) : State()
        data class Error(val message: String) : State()
    }
}