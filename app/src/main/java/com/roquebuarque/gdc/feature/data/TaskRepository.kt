package com.roquebuarque.gdc.feature.data

import androidx.lifecycle.LiveData
import com.roquebuarque.gdc.feature.data.entity.TaskDto
import com.roquebuarque.gdc.feature.data.local.TaskDao

class TaskRepository private constructor(
    private val localDataSource: TaskDao
) {


    suspend fun addTask(taskDto: TaskDto) {
        localDataSource.insert(taskDto)
    }

    fun getAllTasks(): LiveData<List<TaskDto>> = localDataSource.getAllTasks()

    companion object {

        fun create(localDataSource: TaskDao): TaskRepository {

            return TaskRepository(localDataSource)
        }
    }
}