package uz.maverick.movieexplorerdemo.presentation.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import uz.maverick.movieexplorerdemo.domain.entities.MovieItemContainer
import uz.maverick.movieexplorerdemo.utils.POSTER_PATH_154

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    item: MovieItemContainer,
    onClick: () -> Unit,
    toggleFavorite: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = POSTER_PATH_154 + item.movie.poster_path,
                    contentDescription = "Постер фильма",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { toggleFavorite?.invoke() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (item.isInFavorites) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = "Bookmark",
                        tint = if (item.isInFavorites) Color.Red else Color.Gray
                    )
                }
            }
            Text(
                text = item.movie.title.orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp, start = 4.dp, end = 4.dp)
            )
            Text(
                text = "Rating: ${item.movie.vote_average ?: "N/A"}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
            )
        }
    }
}

