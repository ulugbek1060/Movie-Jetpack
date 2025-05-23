package uz.maverick.movieexplorerdemo.presentation.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemContainer
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemEntity

@Composable
fun MainGridWidget(
    pagingItems: LazyPagingItems<MovieItemContainer>,
    loadState: CombinedLoadStates,
    onClick: (Int) -> Unit,
    toggleFavorite: (MovieItemContainer) -> Unit = {},
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
            val item = pagingItems[index]
            if (item != null) {
                MovieItem(
                    item = item,
                    onClick = { onClick(item.movie.id ?: -1) },
                    toggleFavorite = {
                        toggleFavorite.invoke(item)
                    }
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