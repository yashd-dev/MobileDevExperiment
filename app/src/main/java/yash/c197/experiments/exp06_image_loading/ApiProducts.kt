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

class ApiProducts : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentsTheme {
                ApiProductsScreen()
            }
        }
    }
}

data class Product(
    val title: String,
    val price: String,
    val rating: String,
    val image: String
)

@Composable
fun ApiProductsScreen() {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf(false) }

    LaunchedEffect(loading) {
        if (loading) {
            runCatching { getProducts() }
                .onSuccess { products = it }
                .onFailure { error = true }
            loading = false
        }
    }

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("API Products", style = MaterialTheme.typography.headlineMedium)
                    Text("Fetching products from dummyjson.com")
                    Button(onClick = { loading = true; error = false }) {
                        Text("Fetch Again")
                    }
                    if (error) {
                        Text("Could not load products", color = MaterialTheme.colorScheme.error)
                    }
                }
            }

            if (loading) {
                items(6) { LoadingProductCard() }
            } else {
                items(products) { product ->
                    ProductCard(product)
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimpleImage(
                model = product.image,
                description = product.title,
                modifier = Modifier.size(96.dp),
                size = 192
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(product.title, style = MaterialTheme.typography.titleMedium)
                Text("$${product.price} | Rating ${product.rating}")
            }
        }
    }
}

@Composable
fun LoadingProductCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            SimplePlaceholder(modifier = Modifier.size(96.dp))
            Column(modifier = Modifier.padding(start = 12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SimplePlaceholder(modifier = Modifier.fillMaxWidth(0.75f).height(18.dp))
                SimplePlaceholder(modifier = Modifier.fillMaxWidth(0.45f).height(14.dp))
                SimplePlaceholder(modifier = Modifier.fillMaxWidth(0.55f).height(14.dp))
            }
        }
    }
}

private suspend fun getProducts(): List<Product> = withContext(Dispatchers.IO) {
    val api = "https://dummyjson.com/products?limit=50&select=title,price,rating,thumbnail"
    val connection = URL(api).openConnection() as HttpURLConnection
    connection.requestMethod = "GET"

    try {
        val text = connection.inputStream.bufferedReader().use { it.readText() }
        val array = JSONObject(text).getJSONArray("products")

        List(array.length()) { index ->
            val item = array.getJSONObject(index)
            Product(
                title = item.getString("title"),
                price = item.getDouble("price").toString(),
                rating = item.getDouble("rating").toString(),
                image = item.getString("thumbnail")
            )
        }
    } finally {
        connection.disconnect()
    }
}
