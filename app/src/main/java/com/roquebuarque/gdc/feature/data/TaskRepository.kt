package com.roquebuarque.gdc.feature.data

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.roquebuarque.gdc.feature.data.entity.TaskDto
import com.roquebuarque.gdc.feature.data.local.TaskDao

class TaskRepository private constructor(
    private val localDataSource: TaskDao
) {


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addTask(taskDto: TaskDto) {
        localDataSource.insert(taskDto)
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