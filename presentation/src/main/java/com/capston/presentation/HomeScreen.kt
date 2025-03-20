package com.capston.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capston.presentation.theme.CapstonTheme
import com.capston.presentation.theme.MainPurple

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 150.dp),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) // 좌우 여백 추가
            ) {
                items(3) { index -> // TODO 개수 나중에 API로 받아서 수정
                    circleGraph("전체")
                    Spacer(modifier = Modifier.width(16.dp)) // 그래프 간격 추가
                    circleGraph("수분감")
                    Spacer(modifier = Modifier.width(16.dp))
                    circleGraph("믿어봐")
                }
            }

            Spacer(modifier = Modifier.height(32.dp)) // 🌟 그래프와 강의 목록 사이 간격 추가

            LectureList()
        }
    }
}

@Composable
fun LectureList() {
    Column(modifier = Modifier.padding(30.dp)) {
        Text("⭐ 오늘의 강의 (총 3강, 약 42분)", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        val lectures = listOf(
            "1. 함수의 극한과 연속①\n2026 현우진의 수분감 - 수학I (공통) · 약 14분",
            "2. 함수의 극한과 연속①\n2026 현우진의 수분감 - 수학I (공통) · 약 14분",
            "3. 함수의 극한과 연속①\n2026 현우진의 수분감 - 수학I (공통) · 약 14분"
        )

        lectures.forEach { lecture ->
            Text(lecture, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(50.dp))
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
        modifier = Modifier.size(150.dp)
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