package yash.c197.experiments

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import yash.c197.experiments.ui.theme.ExperimentsTheme

class StudentDetails : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ExperimentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = "Welcome Screen",
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center
                                )
                                Button(
                                    onClick = {
                                        startActivity(
                                            Intent(
                                                this@StudentDetails,
                                                StudentDetailsMenuActivity::class.java
                                            )
                                        )
                                    }
                                ) {
                                    Text(text = "Show Student Details")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

class StudentDetailsMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val details = listOf(
            "Name: Yash Deshpande",
            "Roll No: 70322100021",
            "Class: C197 BTI CE",
            "Lives in: Thane"
        )

        setContent {
            ExperimentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val detailsTextColor = MaterialTheme.colorScheme.onSurface.toArgb()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Student Details",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center
                        )
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            AndroidView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp),
                                factory = { context ->
                                    ListView(context).apply {
                                        adapter = object : ArrayAdapter<String>(
                                            context,
                                            android.R.layout.simple_list_item_1,
                                            details
                                        ) {
                                            override fun getView(
                                                position: Int,
                                                convertView: android.view.View?,
                                                parent: ViewGroup
                                            ): android.view.View {
                                                return (super.getView(position, convertView, parent) as TextView).apply {
                                                    gravity = Gravity.CENTER
                                                    setTextColor(detailsTextColor)
                                                    textSize = 18f
                                                }
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
