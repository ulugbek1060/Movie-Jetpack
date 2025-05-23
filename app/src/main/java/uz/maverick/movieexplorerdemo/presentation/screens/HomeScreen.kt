package uz.maverick.movieexplorerdemo.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import coil.compose.AsyncImage
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity
import uz.maverick.movieexplorerdemo.presentation.navigation.Destinations
import uz.maverick.movieexplorerdemo.presentation.viewModels.MainViewModel
import uz.maverick.movieexplorerdemo.presentation.widgets.MovieItem
import uz.maverick.movieexplorerdemo.utils.POSTER_PATH_154

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Explorer Demo") },
                actions = {
                    Icon(
                        Icons.Rounded.Search,
                        contentDescription = "Search icon",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(route = Destinations.SearchScreen.label)
                            }
                    )
                    Icon(
                        Icons.Rounded.FavoriteBorder,
                        contentDescription = "Favorite icon",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(route = Destinations.FavoriteScreen.label)
                            }
                    )
                }
            )
        },
    ) { innerPadding ->
        val pagingItems = viewModel.movies.collectAsLazyPagingItems()
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
                else -> MainGridWidget(pagingItems, loadState) { movieId ->
                    navController.navigate(route = Destinations.DetailScreen.passId(movieId))
                }
            }
        }
    }
}

@Composable
private fun MainGridWidget(
    pagingItems: LazyPagingItems<MovieItemEntity>,
    loadState: CombinedLoadStates,
    onClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = pagingItems.itemCount,
            contentType = pagingItems.itemContentType { "contentType" }
        ) { index ->
            val movie = pagingItems[index]
            if (movie != null) {
                MovieItem(
                    movie = movie,
                    onClick = { onClick(movie.id ?: -1) }
                )
            }
        }
        if (loadState.append is LoadState.Loading) {
            item(span = { GridItemSpan(2) }) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
//                                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        if (loadState.append is LoadState.Error) {
//                            val appendError = (loadState.append as LoadState.Error).error
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Load error!",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun BoxScope.ProgressWidget() {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center),
    )
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
    MovieItem(
        movie = sampleMovie,
        onClick = {}
    )
}