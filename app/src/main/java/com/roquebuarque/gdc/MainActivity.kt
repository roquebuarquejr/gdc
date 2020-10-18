package com.roquebuarque.gdc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.roquebuarque.gdc.feature.data.entity.TaskDto
import com.roquebuarque.gdc.feature.presentation.TaskListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            TaskListViewModel.TaskViewModelFactory(GdcApplication.instance)
        ).get(TaskListViewModel::class.java)

        viewModel.alltasks.observe(this,{
            Log.d("MyTaskList", it.toString())
            Toast.makeText(this, it.size.toString(), Toast.LENGTH_LONG).show()
        })

        viewModel.addTask(TaskDto(name = "test"))
    }
}