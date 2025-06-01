package uz.maverick.movieexplorerdemo.presentation.screens.main

import android.R.attr.rotationY
import androidx.compose.animation.core.R
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uz.maverick.movieexplorerdemo.presentation.navigation.main.BottomBarScreen
import uz.maverick.movieexplorerdemo.utils.Logger

@Composable
fun MainScreen(rootNavController: NavHostController) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.background(Color.White),
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        BottomNavGraph(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            rootNavController = rootNavController
        )
    }
}

@Composable
fun BottomNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    rootNavController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
    ) {
        composable(BottomBarScreen.Home.route) {
            HomeScreen(rootNavController)
        }
        composable(BottomBarScreen.Search.route) {
            SearchScreen(rootNavController)
        }
        composable(BottomBarScreen.Favorite.route) {
            FavoriteScreen(rootNavController)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Favorite,
        BottomBarScreen.Search
    )
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination

    BottomAppBar {
        screens.forEach { screen ->

            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            Logger.d("MainScreen", "Is selected: $isSelected")
            BottomBarItem(
                screen = screen,
                isSelected = isSelected,
                onClick = {
                    if (isSelected) return@BottomBarItem
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.BottomBarItem(
    screen: BottomBarScreen,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color =
        if (isSelected)
            Color.Red.copy(alpha = ContentAlpha.medium)
        else {
            if (isSystemInDarkTheme()) {
                Color.White.copy(alpha = ContentAlpha.medium)
            } else {
                Color.Gray.copy(alpha = ContentAlpha.medium)
            }
        }

    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = null,
                tint = color
            )
        },
        label = {
            Text(
                text = screen.label,
                style = TextStyle(
                    color = color
                )
            )
        },
        selected = isSelected,
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .padding(4.dp)
    )
}

@Preview
@Composable
private fun Preview() {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = uz.maverick.movieexplorerdemo.R.drawable.ic_launcher_background),
            contentDescription = "Gesture Image",
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    rotationZ = rotation
                    translationX = offsetX
                    translationY = offsetY
                }
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, rotate ->
                        scale *= zoom
                        rotation += rotate
                        offsetX += pan.x
                        offsetY += pan.y
                    }
                }
        )
    }
}