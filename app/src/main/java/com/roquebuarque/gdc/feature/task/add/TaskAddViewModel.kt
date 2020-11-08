package com.roquebuarque.gdc.feature.task.add

import android.app.Application
import androidx.lifecycle.*
import com.roquebuarque.gdc.base.AppDataBase
import com.roquebuarque.gdc.feature.task.data.TaskRepository
import com.roquebuarque.gdc.feature.task.data.entity.TaskDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskAddViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val taskId = MutableLiveData<Long>()

    init {
        val dao = AppDataBase.getDataBase(application).taskDao()
        repository = TaskRepository.create(dao)
    }

    fun insert(taskDto: TaskDto) {
        viewModelScope.launch(Dispatchers.IO) {
            taskId.postValue(repository.addTask(taskDto))
        }
    }

    class TaskViewModelFactory constructor(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TaskAddViewModel::class.java)) {
                TaskAddViewModel(
                    this.application
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}