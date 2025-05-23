package uz.maverick.movieexplorerdemo.presentation.widgets

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BoxScope.ProgressWidget() {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center),
    )
}