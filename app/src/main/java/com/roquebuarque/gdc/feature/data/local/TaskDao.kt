package com.roquebuarque.gdc.feature.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roquebuarque.gdc.feature.data.entity.TaskDto

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY name ASC")
    fun getAllTasks(): LiveData<List<TaskDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskDto: TaskDto)
}