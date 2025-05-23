package uz.maverick.movieexplorerdemo.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity
import uz.maverick.movieexplorerdemo.presentation.navigation.Destinations
import uz.maverick.movieexplorerdemo.presentation.viewModels.MainViewModel
import uz.maverick.movieexplorerdemo.presentation.widgets.MainGridWidget
import uz.maverick.movieexplorerdemo.presentation.widgets.ProgressWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Explorer Demo") },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(route = Destinations.SearchScreen.label) },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search",
                        )
                    }
                    IconButton(
                        onClick = { navController.navigate(route = Destinations.FavoriteScreen.label) },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = "Bookmark",
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        val pagingItems = viewModel.moviesState.collectAsLazyPagingItems()
        val loadState = pagingItems.loadState
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                loadState.refresh is LoadState.Loading -> ProgressWidget()
                loadState.refresh is LoadState.Error -> LoadErrorWidget(loadState.refresh)
                pagingItems.itemCount == 0 -> EmptyListWidget()
                else -> MainGridWidget(
                    pagingItems, loadState,
                    onClick = { movieId ->
                        navController.navigate(route = Destinations.DetailScreen.passId(movieId))
                    },
                    toggleFavorite = { movieContainer ->
                        viewModel.toggleFavoriteButton(movieContainer)
                    }
                )
            }
        }
    }
}

@Composable
private fun BoxScope.LoadErrorWidget(refresh: LoadState) {
    val error = (refresh as LoadState.Error).error
    Text(
        text = "ERROR: ${error.localizedMessage ?: "ERROR"}",
        modifier = Modifier
            .align(Alignment.Center)
            .padding(16.dp),
        color = MaterialTheme.colorScheme.error,
    )
}

@Composable
private fun BoxScope.EmptyListWidget() {
    Text(
        text = "Movie list is empty!",
        modifier = Modifier.align(Alignment.Center)
    )
}

@Preview
@Composable
private fun Preview() {
    val sampleMovie = MovieItemEntity(
        adult = false,
        backdrop_path = "/abc123_backdrop.jpg",
        id = 123456,
        original_language = "en",
        original_title = "Sample Original Title",
        overview = "This is a mock overview of the movie for testing purposes.",
        popularity = 456.78,
        poster_path = "/xyz789_poster.jpg",
        release_date = "2024-12-31",
        title = "Sample Movie Title",
        video = false,
        vote_average = 8.4,
        vote_count = 2345
    )
//    MovieItem(
//        movie = sampleMovie,
//        onClick = {}
//    )
}