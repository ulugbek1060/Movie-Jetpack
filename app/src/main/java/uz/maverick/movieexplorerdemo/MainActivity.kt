package uz.maverick.movieexplorerdemo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.maverick.movieexplorerdemo.presentation.navigation.SetupNavController
import uz.maverick.movieexplorerdemo.ui.theme.MovieExplorerDemoTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            android.view.WindowInsets.Type.statusBars()
        }
        setContent {
            MovieExplorerDemoTheme {
                SetupNavController(navController = rememberNavController())
            }
        }
    }
}

