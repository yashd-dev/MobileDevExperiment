package yash.c197.experiments

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yash.c197.experiments.ui.theme.ExperimentsTheme

class SumSeriesExplicitIntent : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SeriesSumScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SeriesSumScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var number by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "SeriesSum",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Find the sum of n terms of the series: 1 + 1/2 + 1/3 + ... + 1/n",
            style = MaterialTheme.typography.bodyLarge
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = number,
                    onValueChange = { number = it },
                    label = { Text("Enter n") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(onClick = {
                    val n = number.toIntOrNull()
                    var result = 0.0

                    if (n != null && n > 0) {
                        for (i in 1..n) {
                            result += 1.0 / i
                        }
                    }

                    val answer = if (n == null || n <= 0) {
                        "Please enter a positive number"
                    } else {
                        "Sum of $n terms is $result"
                    }

                    val intent = Intent(context, SeriesSumResultActivity::class.java)
                    intent.putExtra("answer", answer)
                    context.startActivity(intent)
                }) {
                    Text(text = "SUM THE SERIES")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeriesSumPreview() {
    ExperimentsTheme {
        SeriesSumScreen()
    }
}
