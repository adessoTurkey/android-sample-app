package com.adesso.movee.scene.introduction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.adesso.movee.R

@Composable
fun IndicatorDot(isSelected: Boolean) {
    val indicatorColor =
        if (isSelected) {
            colorResource(id = R.color.introduction_selected_indicator_color)
        } else {
            Color.White
        }
    Box(
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.introduction_indicator_box_size))
            .padding(dimensionResource(id = R.dimen.introduction_indicator_box_padding))
            .background(color = indicatorColor, shape = CircleShape)
    )
}
