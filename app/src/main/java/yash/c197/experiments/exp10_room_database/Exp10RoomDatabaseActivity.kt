package yash.c197.experiments.exp10_room_database

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import yash.c197.experiments.exp10_room_database.data.database.TaskDatabase
import yash.c197.experiments.exp10_room_database.data.entity.Task
import yash.c197.experiments.exp10_room_database.data.repository.TaskRepository
import yash.c197.experiments.exp10_room_database.viewmodel.TaskViewModel
import yash.c197.experiments.exp10_room_database.viewmodel.TaskViewModelFactory
import yash.c197.experiments.ui.theme.ExperimentsTheme

class Exp10RoomDatabaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = TaskDatabase.getInstance(applicationContext)
        val repository = TaskRepository(database.taskDao())

        setContent {
            val viewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(repository)
            )
            val tasks by viewModel.allTasks.collectAsState(initial = emptyList())

            ExperimentsTheme {
                Exp10RoomDatabaseScreen(
                    tasks = tasks,
                    onAdd = viewModel::insert,
                    onUpdate = viewModel::update,
                    onDelete = viewModel::delete,
                    onDeleteAll = viewModel::deleteAll
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exp10RoomDatabaseScreen(
    tasks: List<Task>,
    onAdd: (String, String) -> Unit,
    onUpdate: (Task) -> Unit,
    onDelete: (Task) -> Unit,
    onDeleteAll: () -> Unit
) {
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 28.dp, vertical = 22.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Exp 10 - Room Database",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                }
            }

            item {
                AddTaskCard(onAdd = onAdd)
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Saved Records (${tasks.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    TextButton(
                        onClick = onDeleteAll,
                        enabled = tasks.isNotEmpty()
                    ) {
                        Text("Clear All")
                    }
                }
            }

            if (tasks.isEmpty()) {
                item { EmptyRecordsCard() }
            } else {
                items(tasks, key = { it.id }) { task ->
                    TaskRecordCard(
                        task = task,
                        onEdit = { selectedTask = task },
                        onDelete = { onDelete(task) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(4.dp)) }
        }
    }

    selectedTask?.let { task ->
        UpdateTaskDialog(
            task = task,
            onDismiss = { selectedTask = null },
            onSubmit = { updatedTask ->
                onUpdate(updatedTask)
                selectedTask = null
            }
        )
    }
}

@Composable
private fun AddTaskCard(onAdd: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val canInsert = title.isNotBlank() && description.isNotBlank()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Create Task Record",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    onAdd(title.trim(), description.trim())
                    title = ""
                    description = ""
                },
                enabled = canInsert,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Insert")
            }
        }
    }
}

@Composable
private fun TaskRecordCard(
    task: Task,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Update record")
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete record",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyRecordsCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "No records available",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun UpdateTaskDialog(
    task: Task,
    onDismiss: () -> Unit,
    onSubmit: (Task) -> Unit
) {
    var title by remember(task.id) { mutableStateOf(task.title) }
    var description by remember(task.id) { mutableStateOf(task.description) }
    val updatedTitle = title.trim()
    val updatedDescription = description.trim()
    val hasChanges = updatedTitle != task.title || updatedDescription != task.description
    val canUpdate = updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty() && hasChanges

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update Record") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(
                        task.copy(
                            title = updatedTitle,
                            description = updatedDescription
                        )
                    )
                },
                enabled = canUpdate
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Exp10RoomDatabaseScreenPreview() {
    ExperimentsTheme(dynamicColor = false) {
        Exp10RoomDatabaseScreen(
            tasks = listOf(
                Task(1, "Read Room theory", "Understand Entity, DAO, and Database"),
                Task(2, "Prepare screenshots", "Show insert, update, delete, and view all")
            ),
            onAdd = { _, _ -> },
            onUpdate = {},
            onDelete = {},
            onDeleteAll = {}
        )
    }
}
