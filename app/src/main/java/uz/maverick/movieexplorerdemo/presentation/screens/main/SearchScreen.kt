package uz.maverick.movieexplorerdemo.presentation.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import uz.maverick.movieexplorerdemo.presentation.navigation.main.MainDestinations
import uz.maverick.movieexplorerdemo.presentation.viewModels.SearchMoviesViewModel
import uz.maverick.movieexplorerdemo.presentation.widgets.MainGridWidget
import uz.maverick.movieexplorerdemo.presentation.widgets.ProgressWidget

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchMoviesViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            CustomSearchBar(
                onTextChanged = { text ->
                    viewModel.onSearchQueryChanged(text)
                },
                onSearch = { query ->
                    viewModel.onSearchQueryChanged(query)
                }
            )
        }
    ) { innerPadding ->
        SearchContent(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            viewModel = viewModel
        )
    }
}

@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SearchMoviesViewModel,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val pagingItems = viewModel.moviesState.collectAsLazyPagingItems()
        val loadState = pagingItems.loadState
        when {
            loadState.refresh is LoadState.Loading -> ProgressWidget()
            loadState.refresh is LoadState.Error -> LoadErrorWidget(loadState.refresh)
            pagingItems.itemCount == 0 -> EmptyListWidget()
            else -> MainGridWidget(
                pagingItems, loadState,
                onClick = { movieId ->
                    navController.navigate(
                        route = MainDestinations.MovieDetailsScreen.createRoute(
                            movieId
                        )
                    )
                },
                toggleFavorite = { movieContainer ->
                    viewModel.toggleFavoriteButton(movieContainer)
                }
            )
        }
    }
}

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Search",
    onTextChanged: (String) -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    var query by rememberSaveable { mutableStateOf("") }
    TextField(
        value = query,
        onValueChange = {
            query = it
            onTextChanged(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(8.dp),
        placeholder = { Text(hint) },
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = {
                    onSearch(query)
                }
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        }
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
