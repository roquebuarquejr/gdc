package com.roquebuarque.gdc.feature.task.list.presentation

import android.app.Application
import androidx.lifecycle.*
import com.roquebuarque.gdc.base.AppDataBase
import com.roquebuarque.gdc.feature.task.data.TaskRepository
import com.roquebuarque.gdc.feature.task.data.entity.TaskDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val alltasks: LiveData<List<TaskDto>>

    init {
        val dao = AppDataBase.getDataBase(application).taskDao()
        repository = TaskRepository.create(dao)
        alltasks = repository.getAllTasks()

    }

    fun update(taskDto: TaskDto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(taskDto)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }


    class TaskViewModelFactory constructor(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
                TaskListViewModel(
                    this.application
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}


