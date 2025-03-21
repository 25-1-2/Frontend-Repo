package com.capston.data

data class LectureListDto(
    val total_lesson: Int,
    val lecture_id: Int,
    val tag: String,
    val teacher: String,
    val title: String,
    val platform: String,
    val subject: String
)
