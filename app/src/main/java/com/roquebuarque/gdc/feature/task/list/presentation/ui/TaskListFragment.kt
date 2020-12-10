package com.roquebuarque.gdc.feature.task.list.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.roquebuarque.gdc.GdcApplication
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.task.add.ui.TaskAddActivity
import com.roquebuarque.gdc.feature.task.detail.ui.TaskDetailActivity
import com.roquebuarque.gdc.feature.task.list.presentation.FilterIntent
import com.roquebuarque.gdc.feature.task.list.presentation.TaskListViewModel
import kotlinx.android.synthetic.main.fragment_task_list.*

class TaskListFragment : Fragment() {

    private lateinit var viewModel: TaskListViewModel
    private lateinit var adapter: TaskListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_task_list, container, false)

        activity?.let {
            adapter =
                TaskListAdapter(
                    it,
                    ::taskListClickListener
                )
        }

        viewModel = ViewModelProvider(
            this,
            TaskListViewModel.TaskViewModelFactory(GdcApplication.instance)
        ).get(TaskListViewModel::class.java)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setObserver()
        rvTaskList.adapter = adapter

        fabAddTask.setOnClickListener {

            activity?.let {
                val intent = TaskAddActivity.start(it)
                startActivity(intent)
            }

        }
    }

    private fun taskListClickListener(taskId: Long) {
        activity?.let {
            it.startActivity(TaskDetailActivity.start(it, taskId))
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_task_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_all -> viewModel.deleteAll()
            R.id.action_order_date -> viewModel.filter(FilterIntent.DATE)
            R.id.action_order_abc -> viewModel.filter(FilterIntent.ASC)
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

    private fun setObserver() {
        viewModel.alltasks.observe(viewLifecycleOwner, {
            adapter.submit(it)
        })
    }
}