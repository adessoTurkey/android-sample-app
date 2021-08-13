package com.adesso.movee.scene.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adesso.movee.R
import com.adesso.movee.scene.search.util.loadImage
import com.adesso.movee.uimodel.MultiSearchUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val IMAGE_HEIGHT = 110

@ExperimentalCoroutinesApi
@Composable
fun MultiSearchListItem(
    item: MultiSearchUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_small)),
        modifier = Modifier
            .fillMaxWidth()
            .height(IMAGE_HEIGHT.dp)
            .clickable(onClick = onClick),
        elevation = dimensionResource(id = R.dimen.elevation_small),
    ) {
        Row {
            val image = loadImage(
                url = item.getImagePath() ?: "",
                defaultImage = R.drawable.ic_movee_placeholder
            ).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "Recipe Featured Image",
                    modifier = Modifier
                        .fillMaxHeight(),
                    contentScale = ContentScale.Fit,
                )
            }
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.getTitle(),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                        .padding(all = dimensionResource(id = R.dimen.margin_small)),
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = item.getSubtitle(),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                        .padding(all = dimensionResource(id = R.dimen.margin_small)),
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = dimensionResource(id = R.dimen.margin_small)),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(
                        painter = painterResource(item.getTypeDrawable()),
                        contentDescription = "Icon",
                        tint = colorResource(id = R.color.electric_blue),
                        modifier = Modifier
                            .padding(
                                end = dimensionResource(id = R.dimen.margin_extra_small)
                            )

                    )
                    Text(
                        text = stringResource(item.getTypeStringRes()),
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.text_color_60),
                    )
                }
            }
        }
    }
}

