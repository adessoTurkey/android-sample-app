package com.adesso.movee.scene.introduction

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.adesso.movee.R
import com.adesso.movee.domain.IntroductionPage

private const val PAGE_COUNT = 3
private const val LAST_PAGE_INDEX = 2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroductionScreen(introductionSkipButtonCallback: () -> Unit) {
    val introductionPageList = listOf(
        IntroductionPage(
            imageResId = R.drawable.introduction_first,
            text = stringResource(id = R.string.introduction_title_first)
        ),
        IntroductionPage(
            imageResId = R.drawable.introduction_second,
            text = stringResource(id = R.string.introduction_title_second)
        ),
        IntroductionPage(
            imageResId = R.drawable.introduction_third,
            text = stringResource(id = R.string.introduction_title_third)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.introduction_background_color))
    ) {
        Image(
            painter = painterResource(id = R.drawable.movee_logo),
            contentDescription = null,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.introduction_header_height))
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )

        val pagerState = rememberPagerState()
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(
                    bottom = dimensionResource(id = R.dimen.introduction_slide_bottom_padding)
                ),
            pageCount = PAGE_COUNT
        ) { page ->
            IntroductionSlideView(introductionPageList[page])
        }

        DoneButton(
            introductionSkipButtonCallback,
            text = if (pagerState.currentPage == LAST_PAGE_INDEX) {
                stringResource(id = R.string.introduction_get_started)
            } else {
                stringResource(id = R.string.introduction_skip)
            },
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.introduction_done_button_top_padding))
                .align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.introduction_indicator_padding))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            for (pageIndex in introductionPageList.indices) {
                IndicatorDot(isSelected = pageIndex == pagerState.currentPage)
            }
        }
    }
}
