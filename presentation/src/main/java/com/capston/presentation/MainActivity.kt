package com.capston.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            CapstonTheme {
                MyApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val items: List<Screen> = listOf(
        Screen.Home,
        Screen.Calender,
        Screen.LectureList,
        Screen.Profile,
    )
    var bottomNavState by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center) {
                    }

                },
                modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp)),
                navigationIcon = {
                    // 메뉴
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu icon" )
                    }

                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "alarm icon" )
                    }
                },
            )
        },
        bottomBar = {
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
                            onClick = { bottomNavState = index },
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
                    onClick = { },
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
        },

    ) { contentPadding ->

        Column(
            modifier
                .padding(contentPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CapstonTheme {
        MyApp()
    }
}