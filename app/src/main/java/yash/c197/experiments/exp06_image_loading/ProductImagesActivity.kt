package yash.c197.experiments.exp06_image_loading

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import yash.c197.experiments.ui.theme.ExperimentsTheme

class ProductImagesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentsTheme {
                ProductImagesScreen()
            }
        }
    }
}

data class Product(
    val id: Int,
    val title: String,
    val price: String,
    val rating: String,
    val thumbnail: String
)

@Composable
fun ProductImagesScreen() {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(loading) {
        if (loading) {
            runCatching { fetchProducts() }
                .onSuccess { products = it }
                .onFailure { error = it.message ?: "Failed to load products" }
            loading = false
        }
    }

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.padding(top = 36.dp, bottom = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Product Images", style = MaterialTheme.typography.headlineMedium)
                    Text("Loaded from dummyjson.com/products?limit=0", style = MaterialTheme.typography.bodyMedium)
                    Button(onClick = { loading = true; error = null }) {
                        Text("Refresh")
                    }
                    error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                }
            }

            if (loading) {
                items(6) { ProductSkeletonCard() }
            } else {
                items(products, key = { it.id }) { product ->
                    ProductCard(product)
                }
            }
        }
    }
}

@Composable
private fun ProductCard(product: Product) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier.size(96.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(product.title, style = MaterialTheme.typography.titleMedium)
                Text("$${product.price}  |  Rating ${product.rating}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun ProductSkeletonCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            SkeletonBox(modifier = Modifier.size(96.dp))
            Column(modifier = Modifier.padding(start = 12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SkeletonBox(modifier = Modifier.fillMaxWidth(0.75f).height(18.dp))
                SkeletonBox(modifier = Modifier.fillMaxWidth(0.45f).height(14.dp))
                SkeletonBox(modifier = Modifier.fillMaxWidth(0.55f).height(14.dp))
            }
        }
    }
}

private suspend fun fetchProducts(): List<Product> = withContext(Dispatchers.IO) {
    val url = "https://dummyjson.com/products?limit=0&select=id,title,price,rating,thumbnail"
    val connection = URL(url).openConnection() as HttpURLConnection
    connection.connectTimeout = 10_000
    connection.readTimeout = 10_000
    connection.requestMethod = "GET"

    try {
        val response = connection.inputStream.bufferedReader().use { it.readText() }
        val products = JSONObject(response).getJSONArray("products")
        List(products.length()) { index ->
            val item = products.getJSONObject(index)
            Product(
                id = item.getInt("id"),
                title = item.getString("title"),
                price = item.getDouble("price").toString(),
                rating = item.getDouble("rating").toString(),
                thumbnail = item.getString("thumbnail")
            )
        }
    } finally {
        connection.disconnect()
    }
}
