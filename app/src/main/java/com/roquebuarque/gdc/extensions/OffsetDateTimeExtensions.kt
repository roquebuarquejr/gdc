package com.roquebuarque.gdc.extensions

import com.roquebuarque.gdc.feature.task.data.entity.TaskDateDto
import org.threeten.bp.OffsetDateTime

fun OffsetDateTime.convertToTaskDateDto(): TaskDateDto {
    return TaskDateDto(
        day = dayOfMonth,
        month = monthValue,
        year = year,
        hour = hour,
        minute = minute
    )
}
