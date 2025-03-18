package com.capston.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.capston.presentation.theme.CapstonTheme
import com.capston.presentation.theme.LightGray
import com.capston.presentation.theme.MainPurple
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ->
            CapstonTheme {
                val items: List<Screen> = listOf(
                    Screen.Home,
                    Screen.Calender,
                    Screen.Search,
                    Screen.LectureList,
                    Screen.Profile,
                )
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = Color.White,
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route

                            items.forEach { screen ->
                                NavigationBarItem(
                                    selected = currentRoute == screen.route,
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MainPurple,
                                        unselectedIconColor = LightGray,
                                    ),
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        when (val icon = screen.icon) {
                                            is ImageVector -> {
                                                // 기본 아이콘
                                                Icon(imageVector = icon, contentDescription = screen.route)
                                            }
                                            is Int -> {
                                                // drawable 리소스를 사용할 경우
                                                if (currentRoute == screen.route) {
                                                    // 선택된 상태일 때: 아이콘을 변경
                                                    when (screen.route) {
                                                        "calender" -> Image(painter = painterResource(id = R.drawable.activity_main_calener_iv_on), contentDescription = screen.route)
                                                        "lecture_list" -> Image(painter = painterResource(id = R.drawable.activity_main_lecture_list_iv_on), contentDescription = screen.route)
                                                    }
                                                } else {
                                                    // 기본 상태일 때: 색상만 변경
                                                    Icon(painter = painterResource(id = icon), contentDescription = screen.route)
                                                }
                                            }
                                            else -> {
                                                // 기본 아이콘 처리
                                                Icon(imageVector = Icons.Default.Home, contentDescription = screen.route)
                                            }
                                        }
                                    }
                                )

                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.Home.route) { HomeScreen() }
                        composable(route = Screen.Calender.route) { CalenderScreen() }
                        composable(route = Screen.Search.route) { SearchScreen() }
                        composable(route = Screen.LectureList.route) { LectureListScreen() }
                        composable(route = Screen.Profile.route) { ProfileScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
    }
}

@Composable
fun CalenderScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
    }
}

@Composable
fun SearchScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
    }
}

@Composable
fun LectureListScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
    }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    CapstonTheme {
        HomeScreen()
        ProfileScreen()
    }
}
