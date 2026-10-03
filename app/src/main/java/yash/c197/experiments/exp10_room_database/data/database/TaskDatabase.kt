package yash.c197.experiments.exp10_room_database.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import yash.c197.experiments.exp10_room_database.data.dao.TaskDao
import yash.c197.experiments.exp10_room_database.data.entity.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "exp10_room_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
