package com.adesso.movee.scene.introduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.adesso.movee.R
import com.adesso.movee.domain.IntroductionPage

@Composable
fun IntroductionSlideView(page: IntroductionPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.imageResId),
            contentDescription = null,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = page.text, style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(
                top = dimensionResource(
                    id = R.dimen.introduction_slide_text_top_padding
                )
            ),
            color = Color.White
        )
    }
}
