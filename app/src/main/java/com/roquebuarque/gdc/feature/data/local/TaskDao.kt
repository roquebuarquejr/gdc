package com.roquebuarque.gdc.feature.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.roquebuarque.gdc.feature.data.entity.TaskDto

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY name ASC")
    fun getAllTasks(): LiveData<List<TaskDto>>

    @Query("SELECT * FROM task_table WHERE id = :id")
    fun getTaskById(id: Long): LiveData<TaskDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskDto: TaskDto) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(taskDto: TaskDto)

    @Query("DELETE FROM task_table")
    fun deleteAll()

    @Query("DELETE FROM task_table WHERE id = :id")
    fun deleteById(id: Long)
}