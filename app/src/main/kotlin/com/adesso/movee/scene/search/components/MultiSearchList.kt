package com.adesso.movee.scene.search.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.adesso.movee.R
import com.adesso.movee.uimodel.MultiSearchUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@Composable
fun MultiSearchList(
    listItems: List<MultiSearchUiModel>,
    onClick: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.margin_large)
        ),
        contentPadding = PaddingValues(
            bottom = dimensionResource(id = R.dimen.margin_large)
        ),
        state = listState
    ) {
        items(listItems) { item ->
            MultiSearchListItem(
                item = item,
                modifier = Modifier.fillMaxHeight(),
                onClick = {
                    onClick("go to details.")
                }
            )

        }
    }
}
