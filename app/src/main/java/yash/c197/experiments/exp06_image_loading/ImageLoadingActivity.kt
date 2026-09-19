package yash.c197.experiments.exp06_image_loading

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import yash.c197.experiments.ui.theme.ExperimentsTheme

class ImageLoadingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentsTheme {
                ImageLoadingMenuScreen()
            }
        }
    }
}

@Composable
fun ImageLoadingMenuScreen() {
    val context = LocalContext.current

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 36.dp, bottom = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Exp 6 - Image Loading", style = MaterialTheme.typography.headlineMedium)
                Text(
                    "Choose one clean Glide image-loading demo.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            ImageOptionCard(
                title = "Take Photo",
                description = "Open camera and display the captured image.",
                icon = Icons.Filled.AddAPhoto,
                buttonText = "Open Camera Demo",
                onClick = { context.startActivity(Intent(context, CameraImageActivity::class.java)) }
            )

            ImageOptionCard(
                title = "Show Album Photos",
                description = "Select multiple gallery images and load all of them with Glide.",
                icon = Icons.Filled.PhotoLibrary,
                buttonText = "Open Gallery Demo",
                onClick = { context.startActivity(Intent(context, GalleryImagesActivity::class.java)) }
            )

            ImageOptionCard(
                title = "Fetch Products",
                description = "Fetch product images from dummyjson.com/products.",
                icon = Icons.Filled.CloudDownload,
                buttonText = "Open API Demo",
                onClick = { context.startActivity(Intent(context, ProductImagesActivity::class.java)) }
            )
        }
    }
}

@Composable
fun ImageOptionCard(
    title: String,
    description: String,
    icon: ImageVector,
    buttonText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, modifier = Modifier.padding(end = 12.dp))
                Column {
                    Text(title, style = MaterialTheme.typography.titleMedium)
                    Text(description, style = MaterialTheme.typography.bodySmall)
                }
            }
            Button(onClick = onClick) {
                Text(buttonText)
            }
        }
    }
}
