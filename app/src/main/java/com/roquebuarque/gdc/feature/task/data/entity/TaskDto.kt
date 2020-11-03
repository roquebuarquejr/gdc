package com.roquebuarque.gdc.feature.task.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "task_table")
data class TaskDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val date : OffsetDateTime?= null,
    val state: String = Status.TODO.name
)

enum class Status {
    TODO,
    PROGRESS,
    DONE
}