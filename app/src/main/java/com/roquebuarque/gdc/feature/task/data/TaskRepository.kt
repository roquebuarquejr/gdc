package com.roquebuarque.gdc.feature.task.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.roquebuarque.gdc.feature.task.data.entity.TaskDto
import com.roquebuarque.gdc.feature.task.data.local.TaskDao

class TaskRepository private constructor(
    private val localDataSource: TaskDao
) {


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addTask(taskDto: TaskDto) {
        localDataSource.insert(taskDto)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(taskDto: TaskDto) {
        localDataSource.update(taskDto)
    }

    fun getAllTasks(): LiveData<List<TaskDto>> {
      return localDataSource.getAllTasks()
    }

    companion object {

        fun create(localDataSource: TaskDao): TaskRepository {

            return TaskRepository(localDataSource)
        }
    }
}