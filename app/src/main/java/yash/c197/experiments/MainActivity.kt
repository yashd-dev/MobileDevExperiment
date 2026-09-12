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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yash.c197.experiments.ui.theme.ExperimentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LandingPage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LandingPage(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = 62.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "MAD Lab Activities",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Select an activity to continue",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActivityCard(
                title = "Basic Calculator",
                description = "Enter two numbers and choose an operation.",
                buttonText = "Open Calculator",
                onClick = {
                    context.startActivity(Intent(context, BasicCalculatorActivity::class.java))
                }
            )

            ActivityCard(
                title = "Know Your Number",
                description = "Find factorial and check whether a number is even or odd.",
                buttonText = "Open Activity",
                onClick = {
                    context.startActivity(Intent(context, EvenOdd::class.java))
                }
            )

            ActivityCard(
                title = "SeriesSum",
                description = "Find the sum of 1 + 1/2 + 1/3 + ... + 1/n using explicit intent.",
                buttonText = "Open Activity",
                onClick = {
                    context.startActivity(Intent(context, SumSeriesExplicitIntent::class.java))
                }
            )

            ActivityCard(
                title = "Launch External Apps",
                description = "Open browser, Google Maps, and call dialer using implicit intents.",
                buttonText = "Open Activity",
                onClick = {
                    context.startActivity(Intent(context, LaunchExternalApps::class.java))
                }
            )

            ActivityCard(
                title = "Student Details",
                description = "Open a welcome screen, then show student details in a ListView.",
                buttonText = "Open Activity",
                onClick = {
                    context.startActivity(Intent(context, StudentDetails::class.java))
                }
            )

            ActivityCard(
                title = "Grocery With List View",
                description = "Open Grocery Items with Lazy Loading.",
                buttonText = "Open Activity",
                onClick = {
                    context.startActivity(Intent(context, GroceryItems::class.java))
                }
            )
        }
    }
}

@Composable
fun ActivityCard(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall
            )
            Button(onClick = onClick) {
                Text(text = buttonText,style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingPagePreview() {
    ExperimentsTheme {
        LandingPage()
    }
}
