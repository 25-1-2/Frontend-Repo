package com.capston.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.capston.presentation.theme.CapstonTheme
import com.capston.presentation.theme.LightGray2
import com.capston.presentation.theme.MainPurple
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CapstonTheme {
                SettingTopBottomBar()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingTopBottomBar(modifier: Modifier = Modifier) {
    var bottomNavState by rememberSaveable { mutableStateOf(0) }
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar(navController, bottomNavState, { index -> bottomNavState = index }) },
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.title
        ) {
            composable(Screen.Home.title) { HomeScreen() }
            composable(Screen.Calender.title) { CalenderScreen() }
            composable(Screen.Search.title){ SearchScreen()}
            composable(Screen.LectureList.title) { LectureListScreen() }
            composable(Screen.Profile.title) { ProfileScreen() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Column {
        TopAppBar(
            title = {},
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .height(80.dp),
            navigationIcon = {
                IconButton(onClick = { /* 메뉴 클릭 */ }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu icon",
                        Modifier.size(30.dp)
                    )
                }
            },
            actions = {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier.size(40.dp) // 아이콘 크기와 정렬을 위해 설정
                ) {
                    IconButton(onClick = { /* 알람 클릭 */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "alarm icon",
                            Modifier.size(30.dp)
                        )
                    }
                    BadgedBox(
                        badge = {
                            Badge(
                                modifier = Modifier.offset(x = (-4).dp, y = (4).dp) // 오른쪽 위로 조정
                            ) {
                                Text(text = "3") // TODO 실제 알림 개수로 변경
                            }
                        }
                    ) { }
                }
            },
            )
        Divider(color = LightGray2, thickness = 1.dp)
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    bottomNavState: Int,
    onNavItemClick: (Int) -> Unit
) {
    val items: List<Screen> = listOf(
        Screen.Home,
        Screen.Calender,
        Screen.LectureList,
        Screen.Profile,
    )

    Divider(color = LightGray2, thickness = 1.dp)
    Box(
        Modifier
            .fillMaxWidth()
            .height(70.dp) // BottomNavigationBar 높이 설정
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items.forEachIndexed { index, item ->
                if (index == items.size / 2) {
                    Box(Modifier.weight(1f)) // 중앙 간격을 확보하기 위해 빈 Box 추가
                }

                NavigationBarItem(
                    selected = bottomNavState == index,
                    onClick = {
                        onNavItemClick(index)
                        navController.navigate(item.title) // 클릭 시 해당 화면으로 이동
                    },
                    icon = {
                        when (val icon = if (bottomNavState == index) item.selectedIcon else item.unselectedIcon) {
                            is ImageVector -> Icon(imageVector = icon, contentDescription = item.title)
                            is Int -> Image(painter = painterResource(icon), contentDescription = item.title)
                            else -> {} // 예외 처리
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MainPurple,
                        selectedTextColor = MainPurple,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.Search.title)
            },
            containerColor = MainPurple,
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.Center)
                .offset(y = -20.dp), // FAB이 NavigationBar 위로 떠 있도록 설정
            shape = RoundedCornerShape(50)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "search",
                tint = Color.White
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CapstonTheme {
        SettingTopBottomBar()
    }
}