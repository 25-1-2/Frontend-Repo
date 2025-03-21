package com.capston.presentation.ui

import android.os.Build
import android.util.Log
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.capston.presentation.R
import com.capston.presentation.theme.LightGray40
import com.capston.presentation.theme.MainPurple
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalenderScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 100.dp),
    ) {
        CalendarScreen()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var currentYear by remember { mutableStateOf(LocalDate.now().year) }
    var currentMonth by remember { mutableStateOf(LocalDate.now().monthValue) }

    fun updateMonth(year: Int, month: Int) {
        var newYear = year
        var newMonth = month

        // 월이 0이면 이전 해로 이동
        if (newMonth < 1) {
            newYear -= 1
            newMonth = 12
        }
        // 월이 13이면 다음 해로 이동
        else if (newMonth > 12) {
            newYear += 1
            newMonth = 1
        }

        currentYear = newYear
        currentMonth = newMonth
    }

    Column {
        CustomCalendar(
            year = currentYear,
            month = currentMonth,
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it },
            onMonthChanged = { newYear, newMonth -> updateMonth(newYear, newMonth) }
        )

        selectedDate?.let {
            Text("선택한 날짜: ${it.year}-${it.monthValue}-${it.dayOfMonth}", Modifier.padding(8.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalendar(
    year: Int,
    month: Int,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onMonthChanged: (Int, Int) -> Unit
) {
    val today = LocalDate.now()
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val daysInMonth = YearMonth.of(year, month).lengthOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // 0(일) ~ 6(토)

    val days = (1..daysInMonth).map { firstDayOfMonth.plusDays((it - 1).toLong()) }
    val emptyDays = List(firstDayOfWeek) { null } // 빈칸 채우기

    val daysOfWeek = remember {
        listOf(
            R.string.calender_sun,
            R.string.calender_mon,
            R.string.calender_tue,
            R.string.calender_wed,
            R.string.calender_thu,
            R.string.calender_fri,
            R.string.calender_sat
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onMonthChanged(year, month - 1) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "이전 달")
            }
            Text(
                text = "${year}년 ${month}월",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { onMonthChanged(year, month + 1) }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "다음 달")
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(daysOfWeek) { dayResId ->
                Text(
                    text = stringResource(id = dayResId),
                    color = LightGray40,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp).fillMaxWidth()
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(emptyDays + days) { date ->
                if (date == null) {
                    Box(modifier = Modifier.height(40.dp)) // 빈칸 처리
                } else {
                    val isToday = date == today
                    val isSelected = date == selectedDate
                    val backgroundColor = if (isSelected) MainPurple else Color.Transparent
                    val borderColor = if (isToday) MainPurple else Color.Transparent
                    val textColor = when {
                        isSelected -> Color.White   // 선택된 날짜: 흰색 글자
                        isToday -> MainPurple       // 오늘 날짜: 보라색 글자
                        else -> Color.Black        // 기본 날짜: 검은색 글자
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clickable { onDateSelected(date) }
                            .padding(4.dp)
                            .size(40.dp)
                            .background(backgroundColor, shape = RoundedCornerShape(14.dp))
                            .then(
                                if (isToday) Modifier.border(
                                    width = 2.dp,
                                    color = borderColor,
                                    shape = RoundedCornerShape(14.dp)
                                ) else Modifier
                            )
                    ) {
                        Text(text = date.dayOfMonth.toString(), color = textColor)
                    }
                }
            }
        }
    }
}
