package uz.maverick.movieexplorerdemo.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity
import uz.maverick.movieexplorerdemo.presentation.navigation.Destinations
import uz.maverick.movieexplorerdemo.presentation.viewModels.FavoriteViewModel
import uz.maverick.movieexplorerdemo.presentation.widgets.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoriteMovies = viewModel.moviesState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Movies") },
                actions = {
                    androidx.compose.material3.Icon(
                        Icons.Rounded.Delete,
                        contentDescription = "Favorite icon",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { viewModel.clearAllFavorites() }
                    )
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (favoriteMovies.value.isEmpty()) {
                Text(
                    text = "No favorite movies",
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            } else {
                FavoriteMoviesList(modifier, favoriteMovies.value, navController)
            }
        }
    }
}

@Composable
private fun FavoriteMoviesList(
    modifier: Modifier,
    favoriteMovies: List<MovieItemEntity>,
    navController: NavController
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(favoriteMovies.size) { index ->
            val movie = favoriteMovies[index]
            MovieItem(
                movie = movie,
                onClick = {
                    navController.navigate(route = Destinations.DetailScreen.passId(movie.id ?: 0))
                }
            )
        }
    }
}