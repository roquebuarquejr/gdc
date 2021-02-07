package com.roquebuarque.gdc.feature.task.detail

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.roquebuarque.gdc.base.AppDataBase
import com.roquebuarque.gdc.feature.task.data.TaskRepository
import com.roquebuarque.gdc.feature.task.data.entity.TaskDto
import com.roquebuarque.gdc.job.NotificationUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDetailViewModel(application: Application,
                          private val notificationUtil: NotificationUtil) : AndroidViewModel(application) {

    private lateinit var repository: TaskRepository

    private val _id = MutableLiveData<Long>()
    private var _task: LiveData<TaskDto> = _id
        .switchMap { id ->
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

    fun update(taskDto: TaskDto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(taskDto)
        }
    }

    fun delete(context: Context, taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            notificationUtil.deleteNotification(context, taskId.toInt())
            repository.deleteById(taskId)
        }
    }

    class TaskViewModelFactory constructor(private val application: Application,
                                           private val notificationUtil: NotificationUtil) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TaskDetailViewModel::class.java)) {
                TaskDetailViewModel(
                    this.application,
                    notificationUtil
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}