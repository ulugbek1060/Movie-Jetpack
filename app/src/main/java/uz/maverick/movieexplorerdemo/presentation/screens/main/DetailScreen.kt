package uz.maverick.movieexplorerdemo.presentation.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import uz.maverick.movieexplorerdemo.presentation.viewModels.MovieDetailViewModel
import uz.maverick.movieexplorerdemo.presentation.widgets.ProgressWidget
import uz.maverick.movieexplorerdemo.utils.POSTER_PATH_ORIGINAL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val state = viewModel.movieDetailState.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.getMovieDetail(movieId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (state.value is MovieDetailViewModel.State.Loading) {
                        Text("Loading...")
                    } else if (state.value is MovieDetailViewModel.State.Error) {
                        Text("Error")
                    } else {
                        val movie = (state.value as MovieDetailViewModel.State.Success).data
                        Text(movie.title.orEmpty())
                    }
                },
                actions = {
                    if (state.value is MovieDetailViewModel.State.Success) {
                        val isInFavorite =
                            (state.value as MovieDetailViewModel.State.Success).isInFavorite
                        IconButton(
                            onClick = {  viewModel.toggleFavoriteButton() },
                        ) {
                            Icon(
                                imageVector = if (isInFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                                contentDescription = "Bookmark",
                                tint = if (isInFavorite) Color.Red else Color.Gray,
                            )
                        }
                    }
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.value is MovieDetailViewModel.State.Error -> {
                    Text(
                        text = "Error: ${(state.value as MovieDetailViewModel.State.Error).message}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                state.value is MovieDetailViewModel.State.Loading -> {
                    ProgressWidget()
                }

                state.value is MovieDetailViewModel.State.Success -> {
                    val movie = (state.value as MovieDetailViewModel.State.Success).data
                    Column {
                        AsyncImage(
                            model = POSTER_PATH_ORIGINAL + movie.poster_path,
                            contentDescription = "Постер фильма",
                            modifier = Modifier
                                .height(300.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Text(
                            text = movie.title.orEmpty(),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 8.dp, start = 4.dp, end = 4.dp)
                        )
                        Text(
                            text = "Rating: ${movie.vote_average ?: "N/A"}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
                        )
                        Text(
                            text = movie.overview ?: "Overview",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
                        )
                    }
                }
            }
        }
    }
}