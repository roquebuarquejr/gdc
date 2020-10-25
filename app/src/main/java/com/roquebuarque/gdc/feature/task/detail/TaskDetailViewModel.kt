package com.roquebuarque.gdc.feature.task.detail

import android.app.Application
import androidx.lifecycle.*
import com.roquebuarque.gdc.base.AppDataBase
import com.roquebuarque.gdc.feature.task.data.TaskRepository
import com.roquebuarque.gdc.feature.task.data.entity.TaskDto

class TaskDetailViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var repository: TaskRepository

    private val _id = MutableLiveData<Long>()
    private var _task: LiveData<TaskDto> = _id.switchMap { id ->
        repository.getTaskById(id)
    }

    val task: LiveData<TaskDto> = _task

    init {
        val dao = AppDataBase.getDataBase(application).taskDao()
        repository = TaskRepository.create(dao)
    }

    fun start(id: Long) {
        _id.value = id
    }


    class TaskViewModelFactory constructor(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TaskDetailViewModel::class.java)) {
                TaskDetailViewModel(
                    this.application
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}