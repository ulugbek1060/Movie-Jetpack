package uz.maverick.movieexplorerdemo.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemContainer
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity
import uz.maverick.movieexplorerdemo.domain.repositories.MoviesRepository
import uz.maverick.movieexplorerdemo.domain.repositories.SavedMoviesRepository
import javax.inject.Inject

@HiltViewModel
class SearchMoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val savedMoviesRepository: SavedMoviesRepository,
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val moviePagingFlow: Flow<PagingData<MovieItemEntity>> = searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            repository.searchMovies(query)
        }
        .cachedIn(viewModelScope)

    val moviesState: Flow<PagingData<MovieItemContainer>> = combine(
        moviePagingFlow,
        savedMoviesRepository.getFavoriteMovies()
    ) { pagingData, favoriteMovies ->
        pagingData.map { movie ->
            val isInFavorites = favoriteMovies.any { it.id == movie.id }
            MovieItemContainer(
                movie = movie,
                isInFavorites = isInFavorites
            )
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun toggleFavoriteButton(movie: MovieItemContainer) {
        viewModelScope.launch {
            if (movie.isInFavorites){
                savedMoviesRepository.removeFromFavorites(movie.movie.id ?: 0)
            } else {
                savedMoviesRepository.addToFavorites(movie.movie)
            }
        }
    }
}