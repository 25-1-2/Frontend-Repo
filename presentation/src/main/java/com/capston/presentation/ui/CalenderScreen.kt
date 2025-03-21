package com.capston.presentation.ui

import android.util.Log
import android.widget.CalendarView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CalenderScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 100.dp),
    ) {
        CalendarViewExample()
    }
}

@Composable
fun CalendarViewExample() {
    AndroidView(
        factory = { context ->
            CalendarView(context).apply {
                setOnDateChangeListener { _, year, month, dayOfMonth ->
                    Log.d("CalendarView", "Selected date: $year-${month + 1}-$dayOfMonth")
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}
