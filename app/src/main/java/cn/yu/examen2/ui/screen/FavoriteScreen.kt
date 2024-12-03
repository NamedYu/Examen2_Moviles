package cn.yu.examen2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.yu.examen2.data.Show
import cn.yu.examen2.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onPeliClick: (Show) -> Unit,
    viewModel: FavoriteViewModel
) {
    val state by viewModel.uiState
    LaunchedEffect(Unit) {
        viewModel.getAllFavoriteShows()
    }

    Scaffold (
        modifier = Modifier,
    ) { innerPadding ->
        if(state.favoritesShow.size<=0){
            Column (
                modifier = Modifier.padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ){
                Text(text = "Favorite List is Empty")
            }
        }else {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.favoritesShow.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // crear PeliculaItem para cada objeto
                        rowItems.forEach { pelicula ->
                            PeliculaItem(
                                show = pelicula.toShow(),
                                onPeliClick = onPeliClick,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

            }
        }
    }

}
