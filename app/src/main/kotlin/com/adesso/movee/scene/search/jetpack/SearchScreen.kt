package com.adesso.movee.scene.search.jetpack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adesso.movee.R
import com.adesso.movee.scene.search.SearchViewModel

private const val MAX_LINES = 1

@Composable
fun SearchScreenComposable(
    viewModel: SearchViewModel = hiltViewModel()
) {

    val state = viewModel.multiSearchResults.value

    LaunchedEffect(Unit) {
        viewModel.refreshData()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.search_page_toolbar_height))
            .background(colorResource(id = R.color.colorPrimary))
    ) {

        Column {
            Text(
                text = stringResource(id = R.string.search_message_search),
                style = TextStyle(
                    color = Color.White,
                    fontSize = dimensionResource(id = R.dimen.search_toolbar_title_size).value.sp
                ),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.search_title_horizontal_padding
                    ),
                    vertical = dimensionResource(
                        id = R.dimen.search_title_vertical_padding
                    )
                )
            )

            MovieSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(
                        top = dimensionResource(
                            id = R.dimen.search_bar_top_padding
                        ),
                        bottom = dimensionResource(
                            id = R.dimen.search_bar_bottom_end_start_padding
                        ),
                        start = dimensionResource(
                            id = R.dimen.search_bar_bottom_end_start_padding
                        ),
                        end = dimensionResource(
                            id = R.dimen.search_bar_bottom_end_start_padding
                        )
                    ),
                hint = stringResource(id = R.string.search_message_movies_or_series),
                onSearch = { searchKey ->
                    viewModel.onTextChange(searchKey)
                }
            )

            if (viewModel.shouldShowEmptyResultView.value == true) {
                EmptyScreen()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    items(state) { contentList ->
                        ContentListRow(
                            movie = contentList,
                            onItemClick = {
                                viewModel.onMultiSearchClick(contentList)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieSearchBar(
    modifier: Modifier,
    hint: String,
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .background(Color.Transparent)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                },
            elevation = dimensionResource(id = R.dimen.elevation_small),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_small))
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(
                        id = R.string.search_bar_icon_content_description
                    ),
                    modifier = Modifier
                        .align(CenterVertically)
                        .padding(
                            start = dimensionResource(
                                id = R.dimen.search_bar_icon_start_padding
                            ),
                            top = dimensionResource(
                                id = R.dimen.search_bar_icon_top_bottom_end_padding
                            ),
                            bottom = dimensionResource(
                                id = R.dimen.search_bar_icon_top_bottom_end_padding
                            ),
                            end = dimensionResource(
                                id = R.dimen.search_bar_icon_top_bottom_end_padding
                            )
                        )
                )
                BasicTextField(
                    value = text,
                    onValueChange = { searchKey ->
                        text = searchKey
                        onSearch(text)
                    },
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen.search_text_field_horizontal_padding
                        ),
                        vertical = dimensionResource(
                            id = R.dimen.search_text_field_vertical_padding
                        ),
                    ),
                    maxLines = MAX_LINES,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.body1.copy(color = Color.Black),
                )
            }

            if (isHintDisplayed) {
                Row(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(
                            id = R.string.search_bar_icon_content_description
                        ),
                        modifier = Modifier
                            .align(CenterVertically)
                            .padding(
                                start = dimensionResource(
                                    id = R.dimen.search_bar_icon_start_padding
                                ),
                                top = dimensionResource(
                                    id = R.dimen.search_bar_icon_top_bottom_end_padding
                                ),
                                bottom = dimensionResource(
                                    id = R.dimen.search_bar_icon_top_bottom_end_padding
                                ),
                                end = dimensionResource(
                                    id = R.dimen.search_bar_icon_top_bottom_end_padding
                                )
                            )
                    )

                    Text(
                        text = hint,
                        color = Color.LightGray,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(CenterVertically)
                            .padding(
                                start = dimensionResource(
                                    id = R.dimen.search_hint_text_start_padding
                                ),
                                top = dimensionResource(
                                    id = R.dimen.search_hint_text_top_padding
                                ),
                                bottom = dimensionResource(
                                    id = R.dimen.search_hint_text_bottom_end_padding
                                ),
                                end = dimensionResource(
                                    id = R.dimen.search_hint_text_bottom_end_padding
                                )
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.search_page_empty_view_top_margin))
                .fillMaxWidth()
                .background(Color.White)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_empty_multi_search),
            contentDescription = stringResource(
                id = R.string.search_content_item_empty_view_content_description
            ),
            modifier = Modifier
                .align(CenterHorizontally)
                .height(dimensionResource(id = R.dimen.search_page_empty_view_image_height))
                .width(dimensionResource(id = R.dimen.search_page_empty_view_image_width))
        )
        Text(
            text = stringResource(id = R.string.search_message_empty_search_result),
            modifier = Modifier.align(CenterHorizontally)
        )
    }
}
