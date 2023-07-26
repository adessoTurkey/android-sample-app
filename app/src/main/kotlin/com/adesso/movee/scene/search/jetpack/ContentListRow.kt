package com.adesso.movee.scene.search.jetpack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.adesso.movee.R
import com.adesso.movee.uimodel.MultiSearchUiModel

private const val MAX_LINES = 1

@Composable
fun ContentListRow(
    movie: MultiSearchUiModel,
    onItemClick: (movie: MultiSearchUiModel) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                bottom = dimensionResource(id = R.dimen.margin_small),
                start = dimensionResource(id = R.dimen.margin_large),
                top = dimensionResource(id = R.dimen.margin_small),
                end = dimensionResource(id = R.dimen.margin_large)
            ),
        elevation = dimensionResource(id = R.dimen.elevation_small),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_small))
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .background(Color.White)
                .clickable { onItemClick(movie) }
        ) {
            Image(
                painter = rememberImagePainter(data = movie.getImagePath()),
                contentDescription = movie.getTitle(),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dimensionResource(id = R.dimen.search_list_item_width))
                    .clip(RectangleShape),
                alignment = Alignment.CenterStart
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Top),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    movie.getTitle(),
                    style = TextStyle(
                        fontSize = dimensionResource(
                            id = R.dimen.search_list_item_title_text_size
                        ).value.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    color = colorResource(id = R.color.almost_black),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.margin_small),
                        start = dimensionResource(id = R.dimen.margin_small),
                        end = dimensionResource(id = R.dimen.margin_small)
                    ),
                    maxLines = MAX_LINES
                )

                Text(
                    movie.getSubtitle(),
                    style = TextStyle(
                        fontSize = dimensionResource(
                            id = R.dimen.search_list_item_subtitle_text_size
                        ).value.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    color = colorResource(id = R.color.almost_black),
                    textAlign = TextAlign.Start,
                    maxLines = MAX_LINES,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.margin_small),
                        start = dimensionResource(id = R.dimen.margin_small),
                        end = dimensionResource(id = R.dimen.margin_small)
                    )
                )

                Row {
                    Image(
                        painter = painterResource(id = movie.getTypeDrawableId()),
                        contentDescription = stringResource(
                            id = R.string.search_content_type_icon_content_description
                        ),
                        modifier = Modifier
                            .height(
                                dimensionResource(
                                    id = R.dimen.search_list_item_type_icon_width
                                )
                            )
                            .padding(
                                top = dimensionResource(id = R.dimen.margin_small),
                                start = dimensionResource(id = R.dimen.margin_small)
                            )
                    )
                    Text(
                        "Tv Series",
                        style = TextStyle(
                            fontSize = dimensionResource(
                                id = R.dimen.search_list_item_type_title_text_size
                            ).value.sp
                        ),
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(id = R.color.almost_black_60),
                        textAlign = TextAlign.Start,
                        maxLines = MAX_LINES,
                        modifier = Modifier
                            .align(CenterVertically)
                            .wrapContentHeight()
                            .padding(
                                top = dimensionResource(id = R.dimen.margin_medium),
                                start = dimensionResource(id = R.dimen.margin_extra_small),
                                end = dimensionResource(id = R.dimen.margin_medium),
                                bottom = dimensionResource(id = R.dimen.margin_large)
                            )
                    )
                }
            }
        }
    }
}
