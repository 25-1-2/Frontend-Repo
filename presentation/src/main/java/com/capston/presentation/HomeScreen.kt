package com.capston.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capston.presentation.theme.CapstonTheme
import com.capston.presentation.theme.LightGray
import com.capston.presentation.theme.LightGray3
import com.capston.presentation.theme.LightGray4
import com.capston.presentation.theme.MainPurple

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 66.dp),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 2.dp, color = LightGray4)
                    .background(LightGray3)
                    .padding(vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, // 세로로 정렬
                        horizontalArrangement = Arrangement.SpaceBetween, // 양 끝에 배치
                        modifier = Modifier.fillMaxWidth() // Row를 최대 너비로 설정
                    ) {
                        Text(
                            text = stringResource(R.string.home_status),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 10.dp)
                        )

                        Text(
                            text = stringResource(R.string.home_edit),
                            color = LightGray, // 원하는 색상으로 설정
                            modifier = Modifier
                                .padding(top = 25.dp, end = 20.dp)
                                .clickable {
                                    // 편집 버튼 클릭 시 동작
                                }
                        )
                    }

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        items(3) { index -> // TODO 개수 나중에 API로 받아서 수정
                            circleGraph("전체")
                            Spacer(modifier = Modifier.width(16.dp)) // 그래프 간격 추가
                            circleGraph("수분감")
                            Spacer(modifier = Modifier.width(16.dp))
                            circleGraph("믿어봐")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp)) // 🌟 그래프와 강의 목록 사이 간격 추가

            LectureList()
        }
    }
}

@Composable
fun LectureList() {

    val lectures = mutableListOf<String>(
        "1. 함수의 극한과 연속①\n2026 현우진의 수분감 - 수학I (공통) · 약 14분",
        "2. 함수의 극한과 연속①\n2026 현우진의 수분감 - 수학I (공통) · 약 14분",
        //"3. 함수의 극한과 연속①\n2026 현우진의 수분감 - 수학I (공통) · 약 14분"
    )

    LazyColumn(
        modifier = Modifier.padding(start = 30.dp),
    ) {
        item {
            Text(
                text = "⭐ 오늘의 강의 (총 ${lectures.size}강, 약 42분)",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        // 강의가 없을 경우
        if (lectures.isEmpty()) {
            item {
                Spacer(modifier = Modifier.height(30.dp))
                Text("오늘 강의가 없어요 \uD83D\uDE0A\n" +
                        "푹 쉬고 내일 다시 달려보아요 \uD83C\uDFC3")
            }
        } else {
            // 강의가 있을 경우
            items(lectures) { lecture ->
                Text(lecture, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun circleGraph(name: String) {
    val animatedValue = remember { Animatable(0f) }

    // 특정 값으로 색을 채우는 Animation
    LaunchedEffect(Unit) {
        animatedValue.animateTo(
            targetValue = 100F,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        )
    }

    Canvas(
        modifier = Modifier.size(150.dp) // ✅ 원 그래프 크기
    ) {
        val sizeArc = size / 1.3F
        drawArc(
            color = Color(0xFFE1E2E9),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = true,
            topLeft = Offset((size.width - sizeArc.width) / 2f, (size.height - sizeArc.height) / 2f),
            size = sizeArc,
            style = Stroke(width = 30f)
        )

        drawArc(
            brush = Brush.linearGradient(
                colors = listOf(
                    MainPurple, MainPurple
                ),
                start = Offset.Zero,
                end = Offset.Infinite,
            ),
            startAngle = 270f,
            sweepAngle = animatedValue.value,
            useCenter = false,
            topLeft = Offset(
                (size.width - sizeArc.width) / 2f,
                (size.height - sizeArc.height) / 2f
            ),
            size = sizeArc,
            style = Stroke(width = 30f, cap = StrokeCap.Round)
        )

        drawContext.canvas.nativeCanvas.drawText(
            name,  // 텍스트 내용
            size.width / 2,  // X 위치
            size.height / 2,  // Y 위치
            android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK  // 텍스트 색
                textAlign = android.graphics.Paint.Align.CENTER  // 텍스트 중앙 정렬
                textSize = 50f  // 텍스트 크기
            }
        )

        drawContext.canvas.nativeCanvas.drawText(
            "1/50",  // 텍스트 내용
            size.width / 2,  // X 위치
            size.height / 2 + 70,  // Y 위치
            android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK  // 텍스트 색
                textAlign = android.graphics.Paint.Align.CENTER  // 텍스트 중앙 정렬
                textSize = 50f  // 텍스트 크기
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CapstonTheme {
        HomeScreen()
    }
}