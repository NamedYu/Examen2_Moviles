package cn.yu.examen2.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import cn.yu.examen2.data.DataProvider
import cn.yu.examen2.data.Show
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    peliculalista: List<Show>,
    onPeliClick: (Show) -> Unit
) {
    val textFieldState = rememberTextFieldState()
    var expanded by rememberSaveable { mutableStateOf(false) }
    var searchItem by remember { mutableStateOf("") }

    // Filter the list based on the search query
    var searchResults by remember { mutableStateOf<List<Show>>(emptyList())  }
    var errorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    fun searchShows(query: String) {
        coroutineScope.launch {
            DataProvider.searchShows(query = query, onError = {
                errorMessage = it.message ?: "Error occurred"
            }, onSuccess = { results ->
                searchResults = results // Update
            })
        }
    }
    searchShows("a")
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Search bar implementation
            Column (
                modifier = Modifier
            ) {
                SearchBar(
                    modifier = Modifier
                        .semantics { traversalIndex = 0f }
                        .fillMaxWidth(),
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = searchItem,
                            onQueryChange = { searchItem = it
                                            searchShows(searchItem)},
                            onSearch = { expanded = false
                                searchShows(searchItem)},
                            expanded = expanded,
                            onExpandedChange = { expanded = it
                                },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = true,
                            placeholder = { Text("Search Flick with name...") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Clear",
                                    modifier = Modifier.clickable {
                                        searchItem = ""
                                        expanded = false
                                    }
                                )
                            },
                            colors = SearchBarDefaults.inputFieldColors(),
                            interactionSource = remember { MutableInteractionSource() }
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        searchResults.chunked(2).forEach { rowItems ->

                            rowItems.forEach { pelicula ->
//                                    PeliculaItem(
//                                        pelicula = pelicula,
//                                        onPeliClick = onPeliClick,
//                                        modifier = Modifier.weight(1f)
//                                    )
                                pelicula.name?.let {
                                    Text(
                                        text = it,
                                        modifier = Modifier.padding(8.dp)
                                            .fillMaxWidth()
                                            .clickable {
                                                searchItem = it
                                                expanded = false
                                            }
                                    )
                                }
                            }

                        }
                    }
                }

                // Display the filtered list
                if(searchResults.isEmpty()){
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)

                    ){
                        Text("No Founded")
                    }
                }else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        searchResults.chunked(2).forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Create a PeliculaItem for each movie in the row
                                rowItems.forEach { pelicula ->
                                    PeliculaItem(
                                        show = pelicula,
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
    }

}
