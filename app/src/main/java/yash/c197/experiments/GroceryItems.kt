package yash.c197.experiments

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import yash.c197.experiments.ui.theme.ExperimentsTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class GroceryItems : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GroceryItems(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


val groceryItems = listOf(
    GroceryList(
        1,
        "Apples",
        2.99,
        "https://images.pexels.com/photos/102104/pexels-photo-102104.jpeg",
        groceryDate(0)
    ),
    GroceryList(
        2,
        "Bananas",
        1.50,
        "https://images.pexels.com/photos/61127/pexels-photo-61127.jpeg",
        groceryDate(0)
    ),
    GroceryList(
        3,
        "Milk",
        3.49,
        "https://images.pexels.com/photos/248412/pexels-photo-248412.jpeg",
        groceryDate(-1)
    ),
    GroceryList(
        4,
        "Bread",
        2.25,
        "https://images.pexels.com/photos/209206/pexels-photo-209206.jpeg",
        groceryDate(1)
    ),
    GroceryList(
        5,
        "Eggs",
        4.00,
        "https://images.pexels.com/photos/162712/egg-white-food-protein-162712.jpeg",
        groceryDate(1)
    )
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryItems(modifier: Modifier= Modifier){
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(groceryDate(0)) }
    var showDatePicker by remember { mutableStateOf(false) }
    val filteredItems = groceryItems.filter {
        it.availabilityDate == selectedDate && it.title.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search groceries") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { showDatePicker = true }) {
            Text("Available on: $selectedDate")
        }

        if (showDatePicker) {
            val datePickerState = androidx.compose.material3.rememberDatePickerState(
                initialSelectedDateMillis = groceryDateMillis(selectedDate)
            )
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedDate = groceryDate(datePickerState.selectedDateMillis ?: groceryDateMillis(selectedDate))
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredItems.size) { index ->
                val item = filteredItems[index]
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(context, GroceryItemDetailActivity::class.java).apply {
                                putExtra("title", item.title)
                                putExtra("price", item.price)
                                putExtra("imageUrl", item.imageUrl)
                                putExtra("availabilityDate", item.availabilityDate)
                            }
                            context.startActivity(intent)
                        },
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
                ) {
                    ListItem(
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        leadingContent = {
                            AsyncImage(
                                model = item.imageUrl,
                                contentDescription = item.title,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(48.dp)
                                    .height(48.dp)
                            )
                        },
                        headlineContent = { Text(item.title) },
                        supportingContent = { Text("Price: $${item.price} • ${item.availabilityDate}") },
                        trailingContent = {
                            Image(imageVector = Icons.Default.Add, contentDescription = "Add ${item.title}")
                        }
                    )
                }
            }
        }
    }
}

private fun groceryDate(dayOffset: Int): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, dayOffset)
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.time)
}

private fun groceryDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(millis)
}

private fun groceryDateMillis(date: String): Long {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.parse(date)?.time ?: 0L
}
