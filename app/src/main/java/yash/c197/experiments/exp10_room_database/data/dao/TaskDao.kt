package yash.c197.experiments.exp10_room_database.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import yash.c197.experiments.exp10_room_database.data.entity.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM exp10_tasks")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM exp10_tasks ORDER BY id DESC")
    fun getAllTasks(): Flow<List<Task>>
}
