data class LectureListDto(
    val lectures: List<LectureDto>
)

data class LectureDto(
    val duration: Int, // 강의 시간
    val lecture_id: String, // 강의 번호
    val lesson: String,
)
