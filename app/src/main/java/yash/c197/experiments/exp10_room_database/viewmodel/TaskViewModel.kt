package yash.c197.experiments.exp10_room_database.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import yash.c197.experiments.exp10_room_database.data.entity.Task
import yash.c197.experiments.exp10_room_database.data.repository.TaskRepository

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    val allTasks = repository.allTasks

    fun insert(title: String, description: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTask(Task(title = title, description = description))
    }

    fun update(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateTask(task)
    }

    fun delete(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTask(task)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllTasks()
    }
}
