package com.capston.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val title: String,
    val selectedIcon: Any,
    val unselectedIcon: Any,
    val hasBadge: Boolean,
    val messages: Int
) {
    data object Home: Screen(
        title = "home",
        selectedIcon = R.drawable.activity_main_home_iv_on,
        unselectedIcon = R.drawable.activity_main_home_iv,
        hasBadge = false,
        messages = 0
    )

    data object Calender: Screen(
        title = "calender",
        selectedIcon = R.drawable.activity_main_calener_iv_on,
        unselectedIcon = R.drawable.activity_main_calender_iv,
        hasBadge = false,
        messages = 0
    )

    data object Search: Screen(
        title = "search",
        selectedIcon = Icons.Default.Search,
        unselectedIcon = Icons.Default.Search,
        hasBadge = true,
        messages = 12
    )

    data object LectureList: Screen(
        title = "calender",
        selectedIcon = R.drawable.activity_main_lecture_list_iv_on,
        unselectedIcon = R.drawable.activity_main_lecture_list_iv,
        hasBadge = false,
        messages = 0
    )

    data object Profile: Screen(
        title = "profile",
        selectedIcon = R.drawable.activity_main_profile_iv_on,
        unselectedIcon = R.drawable.activity_main_profile_iv,
        hasBadge = false,
        messages = 0
    )
}