package com.adesso.movee.scene.introduction

import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.adesso.movee.R

@Composable
fun DoneButton(
    introductionSkipButtonCallback: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = introductionSkipButtonCallback,
        modifier = modifier.width(dimensionResource(id = R.dimen.introduction_done_button_width)),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
    ) {
        Text(text = text)
    }
}
