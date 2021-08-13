package com.adesso.movee.scene.search.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adesso.movee.scene.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    viewModel: SearchViewModel
) {

    val result = viewModel.results.value
    val query = viewModel.query.value
    val isResultLoaded = viewModel.isResultLoaded.value

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            SearchAppBar(
                query = query,
                onQueryChanged = { viewModel.onQueryChanged(it) },
                onExecuteSearch = { viewModel.onTextChange(query) }
            )
        },
        scaffoldState = scaffoldState,
    ) {
        Box(
            modifier = Modifier
                .padding(24.dp)
        ) {
            if (isResultLoaded) {
                SearchEmptyScreen()
            } else {
                MultiSearchList(
                    listItems = result,
                    onClick = {}
                )
            }
        }
    }
}
