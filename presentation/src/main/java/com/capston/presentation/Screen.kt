package com.capston.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: Any
) {
    data object Home: Screen(
        route = "home",
        resourceId = R.string.home,
        icon = Icons.Default.Home
    )

    data object Calender: Screen(
        route = "calender",
        resourceId = R.string.calender,
        icon = R.drawable.activity_main_calender_iv
    )

    data object LectureList: Screen(
        route = "lecture_list",
        resourceId = R.string.lecture_list,
        icon = R.drawable.activity_main_lecture_list_iv
    )

    data object Search: Screen(
        route = "search",
        resourceId = R.string.search,
        icon = Icons.Default.Search
    )

    data object Profile: Screen(
        route = "profile",
        resourceId = R.string.profile,
        icon = Icons.Default.AccountCircle
    )
}