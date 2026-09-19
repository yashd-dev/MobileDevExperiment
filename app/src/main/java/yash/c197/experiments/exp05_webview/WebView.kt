package yash.c197.experiments.exp05_webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import yash.c197.experiments.ui.theme.ExperimentsTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebView as AccompanistWebView
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class YashdWebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentsTheme {
                SimpleWebViewScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SimpleWebViewScreen(
    initialUrl: String = "https://yashd.in"
) {
    val state = rememberWebViewState(url = initialUrl)

    val navigator = rememberWebViewNavigator()

    var urlText by rememberSaveable { mutableStateOf(initialUrl) }
    LaunchedEffect(state.lastLoadedUrl) {
        state.lastLoadedUrl?.let { urlText = it }
    }

    Scaffold(
        topBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = urlText,
                        onValueChange = { urlText = it },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        label = { Text("URL") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        navigator.loadUrl(urlText.toUrlOrGoogleSearch())
                    }) {
                        Text("Go")
                    }
                }

                when (val loadingState = state.loadingState) {
                    is LoadingState.Loading -> {
                        LinearProgressIndicator(
                            progress = loadingState.progress,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    else -> {}
                }
            }
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = { navigator.navigateBack(); },
                        enabled = navigator.canGoBack
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }

                    IconButton(
                        onClick = {
                            navigator.navigateForward()
                        },
                        enabled = navigator.canGoForward
                    ) {
                        Icon(Icons.Filled.ArrowForward, contentDescription = "Forward")
                    }

                    IconButton(onClick = { navigator.reload() }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Reload")
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            WebView(
                state = state,
                navigator = navigator,
                modifier = Modifier.fillMaxSize(),
                onCreated = { webView ->
                    webView.settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        builtInZoomControls = true
                        displayZoomControls = false
                    }
                }
            )

            val errorState = state.errorsForCurrentRequest.lastOrNull()
            if (errorState != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Failed to load page")
                        Text(errorState.error.description?.toString() ?: "Unknown error")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { navigator.reload() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

private fun String.toUrlOrGoogleSearch(): String {
    val input = trim()
    if (input.startsWith("http://") || input.startsWith("https://")) {
        return input
    }

    if (input.contains('.') && !input.contains(' ')) {
        return "https://$input"
    }

    val query = URLEncoder.encode(input, StandardCharsets.UTF_8.toString())
    return "https://www.google.com/search?q=$query"
}
