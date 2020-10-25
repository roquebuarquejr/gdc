package com.roquebuarque.gdc.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.roquebuarque.gdc.feature.task.data.entity.TaskDto
import com.roquebuarque.gdc.feature.task.data.local.TaskDao

@Database(entities = [TaskDto::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase =
            instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also {
                    instance = it
                }
            }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(context, AppDataBase::class.java, "task_table")
                .fallbackToDestructiveMigration()
                .build()
    }
}