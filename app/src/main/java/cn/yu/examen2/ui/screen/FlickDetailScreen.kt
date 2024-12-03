package cn.yu.examen2.ui.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.yu.examen2.data.FavoriteShow
import cn.yu.examen2.data.ShowDetail
import cn.yu.examen2.viewmodel.FavoriteViewModel
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope as rememberCoroutineScope

@Composable
fun FlickDetailPage(
    modifier: Modifier = Modifier,
    showDetail: ShowDetail,
    onBack: () -> Unit,
    viewModel: FavoriteViewModel
) {
    var isFavorite by remember { mutableStateOf(false) }
    var icon by remember { mutableStateOf(Icons.Filled.FavoriteBorder) }


    LaunchedEffect(showDetail.id) {
        viewModel.isFavoriteShow(showDetail.id) { result ->
            isFavorite = result
        }
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(modifier = modifier) { innerPadding ->
        FlickDetail(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            showDetail,
            isFavorite,
            onFavoriteClick = {
                coroutineScope.launch {
                    if (isFavorite) {
                        showDetail.name?.let {
                            FavoriteShow(
                                showDetail.id,
                                it,
                                showDetail.image?.medium
                            )
                        }?.let { viewModel.deleteFavoriteShow(it) }
                    } else {
                        showDetail.name?.let {
                            FavoriteShow(
                                showDetail.id,
                                it,
                                showDetail.image?.medium
                            )
                        }?.let {
                            viewModel.addFavoriteShow(
                                it
                            )
                        }
                    }
                    isFavorite = !isFavorite
                    icon = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                }
            }
        )
    }
}


@Composable
fun FlickDetail(
    modifier: Modifier = Modifier,
    show: ShowDetail,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit // Now this is just a regular function type
) {
    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
//        horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Card(
                    modifier = Modifier
                        .padding(8.dp)

                ) {
                    Box(
                        modifier = Modifier
                            .size(150.dp, 200.dp)
                            .fillMaxWidth()
                    ) {
                        val imageUrl = show.image?.original ?: "your_default_image_url"
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = show.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop // Cortar imagen
                        )
                        //Rating
                        show.rating?.average.let {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(4.dp)
                                    .background(
                                        Color(0xFFBBE1FA),
                                        shape = RoundedCornerShape(12.dp, 12.dp, 12.dp, 12.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp) // 内部文字的边距
                            ) {
                                Text(
                                    text = "$it",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ){
                    Text(
                        text = show.name ?: "Unknown",
                        style = MaterialTheme.typography.titleLarge
                            .copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            pushStyle(SpanStyle(fontSize = 12.sp))
                            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                            append("Genres: ")
                            pop()
                            append(show.genres?.joinToString(separator = ","))
                        },
//                    style = MaterialTheme.typography.bodySmall
//                        .copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(vertical = 1.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            pushStyle(SpanStyle(fontSize = 12.sp))
                            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                            append("Premiered: ")
                            pop()
                            append(show.premiered)
                        },
//                    style = MaterialTheme.typography.titleLarge
//                        .copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(vertical = 1.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            pushStyle(SpanStyle(fontSize = 12.sp))
                            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                            append("Country: ")
                            pop()
                            append(show.network?.country?.name+","+show.network?.country?.code)
                        },
//                    style = MaterialTheme.typography.titleLarge
//                        .copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(vertical = 1.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            pushStyle(SpanStyle(fontSize = 12.sp))
                            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                            append("Lenguage: ")
                            pop()
                            append(show.language)
                        },
//                    style = MaterialTheme.typography.titleLarge
//                        .copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(vertical = 1.dp)
                    )
                }
            }
            Divider(modifier = Modifier.padding(vertical = 15.dp))
            Text(
                text = "Summary",
                style = MaterialTheme.typography.bodyLarge
                    .copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            val texto = show.summary?.replace("<p>", "")
                ?.replace("</p>", "")
                ?.replace("<b>", "")
                ?.replace("</b>", "")
            Text(
                text = texto?:"NULL",

                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 10.dp)
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            ShareButton(show) // Agregar share button
            modifier.padding()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            FavoriteButton(isFavorite, onClick = onFavoriteClick)//Agregar favorite button
        }
    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit // Now this is just a regular function type
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(12.dp)
    ) {
        if (isFavorite) {
            Icon(Icons.Default.Favorite, "isFavorite", tint = Color.Red)
        } else {
            Icon(Icons.Default.FavoriteBorder, "noFavorite")
        }
    }
}

@Composable
fun ShareButton(pelicula: ShowDetail){
    val context = LocalContext.current
    val shareText = "Check out this link: ${pelicula.officialSite}"

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }

    IconButton(
        onClick = {
            context.startActivity(Intent.createChooser(shareIntent, null))
        },
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .padding(12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Share",
            tint = Color.White
        )
    }
}