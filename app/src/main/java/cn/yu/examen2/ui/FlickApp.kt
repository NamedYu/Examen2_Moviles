package cn.yu.examen2.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cn.yu.examen2.navigation.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickApp(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home","Search","Favorite")
    val routes = listOf("FlickLista", "FlickSearchList", "FlickFavoriteList") // 与导航的 route 对应
    val currentRoute = navHostController.currentBackStackEntryFlow.collectAsState(initial = null).value?.destination?.route
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    IconButton(onClick = {
                        navHostController.navigateUp()
                    }){
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    } }
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = when (item) {
                                    "Home" -> Icons.Default.Home
                                    "Search" -> Icons.Default.Search
                                    else -> Icons.Default.Favorite
                                },
                                contentDescription = item
                            )
                        },
                        label = { Text(item) },
                        selected = currentRoute == routes[index],
                        onClick = {
                            val route = when(item){"Home" -> "FlickLista"
                                "Search" -> "FlickSearchList"
                                "Favorite" -> "FavoriteList"
                                else -> "FlickLista"}
                            navHostController.navigate(route)
                        }
                    )

                }
            }
        }
    ) { innerPadding ->
        AppNavigation(
            modifier = modifier.padding(innerPadding),
            navHostController = navHostController
        )


    }
}