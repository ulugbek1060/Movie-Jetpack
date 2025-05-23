package uz.maverick.movieexplorerdemo.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemContainer
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity
import uz.maverick.movieexplorerdemo.presentation.navigation.Destinations
import uz.maverick.movieexplorerdemo.presentation.viewModels.FavoriteViewModel
import uz.maverick.movieexplorerdemo.presentation.widgets.MovieItem
import uz.maverick.movieexplorerdemo.utils.POSTER_PATH_154

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
                    Icon(
                        Icons.Rounded.Delete,
                        contentDescription = "Favorite icon",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { viewModel.clearAllFavorites() }
                    )
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (favoriteMovies.value.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = null
                    )
                    Text(
                        text = "No favorite movies",
                    )
                }
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
                item = movie,
                onClick = {
                    navController.navigate(route = Destinations.DetailScreen.passId(movie.id ?: 0))
                }
            )
        }
    }
}

 @Composable
private fun MovieItem(
    modifier: Modifier = Modifier,
    item: MovieItemEntity,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Column {
            AsyncImage(
                model = POSTER_PATH_154 + item.poster_path,
                contentDescription = "Постер фильма",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = item.title.orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp, start = 4.dp, end = 4.dp)
            )

            Text(
                text = "Rating: ${item.vote_average ?: "N/A"}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
            )
        }
    }
}