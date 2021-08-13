package com.adesso.movee.scene.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adesso.movee.R

@Composable
fun SearchEmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            Image(
                painterResource(id = R.drawable.ic_empty_multi_search),
                contentDescription = "Recipe Featured Image",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_small)))
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.search_message_empty_search_result),
                color = colorResource(id = R.color.vibrant_blue),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
            )
        }

    }
}
