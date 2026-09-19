package yash.c197.experiments.exp06_image_loading

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import yash.c197.experiments.ui.theme.ExperimentsTheme

class Exp6Menu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentsTheme {
                Exp6MenuScreen()
            }
        }
    }
}

@Composable
fun Exp6MenuScreen() {
    val context = LocalContext.current

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Exp 6 - Image Loading", style = MaterialTheme.typography.headlineMedium)
            Text("Trying camera, gallery and API images using Glide.")

            MenuCard("Take Photo", "Capture image from camera") {
                context.startActivity(Intent(context, CameraPhoto::class.java))
            }
            MenuCard("Gallery Photos", "Pick many images from gallery") {
                context.startActivity(Intent(context, GalleryPhotos::class.java))
            }
            MenuCard("API Products", "Load product images from internet") {
                context.startActivity(Intent(context, ApiProducts::class.java))
            }
        }
    }
}

@Composable
fun MenuCard(title: String, description: String, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(description, style = MaterialTheme.typography.bodyMedium)
            Button(onClick = onClick) {
                Text("Open")
            }
        }
    }
}
