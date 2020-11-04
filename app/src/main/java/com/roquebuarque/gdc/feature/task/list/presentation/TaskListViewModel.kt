package com.roquebuarque.gdc.feature.task.list.presentation

import android.app.Application
import androidx.lifecycle.*
import com.roquebuarque.gdc.base.AppDataBase
import com.roquebuarque.gdc.feature.task.data.TaskRepository
import com.roquebuarque.gdc.feature.task.data.entity.TaskDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var repository: TaskRepository
    private val _filter = MutableLiveData<FilterIntent>()

    private val _alltasks : LiveData<List<TaskDto>> = _filter
    .switchMap {
        when (it) {
            FilterIntent.ASC -> repository.getAllTasks()
            FilterIntent.DATE -> repository.getAllTaskOrderByDate()
            else -> {
                throw java.lang.IllegalArgumentException("Unknown filter option $it")
            }
        }
    }

    val alltasks: LiveData<List<TaskDto>> = _alltasks

    init {
        val dao = AppDataBase.getDataBase(application).taskDao()
        repository = TaskRepository.create(dao)
        filter(FilterIntent.ASC)
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

    fun filter(intent: FilterIntent){
        _filter.value = intent
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


enum class FilterIntent{
    ASC,
    DATE
}


