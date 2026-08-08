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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "MAD Lab Activities",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Select an activity to continue",
            style = MaterialTheme.typography.bodyLarge
        )

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
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
            Button(onClick = onClick) {
                Text(text = buttonText)
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
