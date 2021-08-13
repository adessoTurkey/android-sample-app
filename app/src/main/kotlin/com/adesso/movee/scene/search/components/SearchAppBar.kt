package com.adesso.movee.scene.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.adesso.movee.R

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .height(
                dimensionResource(
                    id = R.dimen.height_main_tab_toolbar
                )
            )
            .fillMaxWidth(),
        color = colorResource(id = R.color.colorPrimary),
        elevation = dimensionResource(id = R.dimen.elevation_small),
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.search_message_search),
                color = Color.White,
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.margin_xxl),
                        top = dimensionResource(id = R.dimen.margin_extra_large),
                        end = dimensionResource(id = R.dimen.margin_xxl),
                        bottom = dimensionResource(id = R.dimen.margin_extra_large)
                    ),
            )
            TextField(
                value = query,
                onValueChange = {
                    onQueryChanged(it)
                    onExecuteSearch()
                },
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(dimensionResource(id = R.dimen.margin_small))
                    .align(CenterHorizontally),
                textStyle = MaterialTheme.typography.body1,
                placeholder = {
                    Text(
                        text = stringResource(
                            id = R.string.search_message_movies_or_series
                        )
                    )
                },
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_small)),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onExecuteSearch()
                        keyboardController?.hide()
                    }
                ),
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Clear Icon",
                        modifier = Modifier
                            .clickable {
                                onQueryChanged("")
                            }
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorResource(id = R.color.white)
                )
            )
        }
    }
}
