package yash.c197.experiments.exp10_room_database.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exp10_tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String
)
