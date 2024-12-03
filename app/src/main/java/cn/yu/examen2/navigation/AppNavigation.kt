package cn.yu.examen2.navigation


import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cn.yu.examen2.data.ShowsRepository
import cn.yu.examen2.ui.screen.FavoriteScreen
import cn.yu.examen2.ui.screen.FlickDetailPage
import cn.yu.examen2.ui.screen.FlickListScreen
import cn.yu.examen2.ui.screen.SearchScreen
import cn.yu.examen2.viewmodel.FavoriteViewModel
import cn.yu.examen2.viewmodel.FlickDetailViewModel
import cn.yu.examen2.viewmodel.FlickListViewModel
import cn.yu.examen2.viewmodel.SearchViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    DataViewModel: FavoriteViewModel = viewModel(factory = FavoriteViewModel.Factory),
){
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = "FlickLista"
    ){

        composable("FlickLista") {
            val vs: FlickListViewModel = viewModel()
            FlickListScreen(
                modifier=modifier,
                peliculalista =vs.flickList.value,
                onPeliClick ={pelicula ->
                    navHostController.navigate(FlickDetailData(peliculaId = pelicula.id))
                },
            )
        }

        composable<FlickDetailData> {
            val vs: FlickDetailViewModel = viewModel()
            val pelicula by vs.pelicula.observeAsState()

            pelicula?.let { showDetail ->
                FlickDetailPage(
                    modifier = modifier,
                    showDetail = showDetail, //
                    onBack = { navHostController.navigateUp() },
                    DataViewModel
                )
            } ?: run {//Si pelicula es null, mostrar error message
                CircularProgressIndicator(modifier = Modifier)
            }
        }
        composable("FlickSearchList") {
            val vs: SearchViewModel = viewModel()
            SearchScreen(
                modifier=modifier,
                peliculalista =vs.flickList.value,
                onPeliClick ={pelicula ->
                    navHostController.navigate(FlickDetailData(peliculaId = pelicula.id))
                }
            )
        }
        composable("FavoriteList") {
            FavoriteScreen(
                modifier=modifier,
                onPeliClick ={pelicula ->
                    navHostController.navigate(FlickDetailData(peliculaId = pelicula.id))
                },
                DataViewModel
            )
        }

    }
}