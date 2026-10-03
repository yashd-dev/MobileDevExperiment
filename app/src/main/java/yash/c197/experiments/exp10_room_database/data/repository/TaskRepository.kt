package yash.c197.experiments.exp10_room_database.data.repository

import yash.c197.experiments.exp10_room_database.data.dao.TaskDao
import yash.c197.experiments.exp10_room_database.data.entity.Task

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks = taskDao.getAllTasks()

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    suspend fun deleteAllTasks() = taskDao.deleteAllTasks()
}
