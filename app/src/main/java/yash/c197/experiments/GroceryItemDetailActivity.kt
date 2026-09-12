package yash.c197.experiments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import yash.c197.experiments.ui.theme.ExperimentsTheme

class GroceryItemDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val title = intent.getStringExtra("title").orEmpty()
        val price = intent.getDoubleExtra("price", 0.0)
        val imageUrl = intent.getStringExtra("imageUrl").orEmpty()
        val availabilityDate = intent.getStringExtra("availabilityDate").orEmpty()

        setContent {
            ExperimentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GroceryItemDetail(
                        title = title,
                        price = price,
                        imageUrl = imageUrl,
                        availabilityDate = availabilityDate,
                        onBack = { finish() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GroceryItemDetail(
    title: String,
    price: Double,
    imageUrl: String,
    availabilityDate: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = onBack) {
            Text("Back")
        }

        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, style = MaterialTheme.typography.headlineSmall)
            Text("$${price}", style = MaterialTheme.typography.titleLarge)
        }

        Text("Available on: $availabilityDate", style = MaterialTheme.typography.bodyLarge)
    }
}
