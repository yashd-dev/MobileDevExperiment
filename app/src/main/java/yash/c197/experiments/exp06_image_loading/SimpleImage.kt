package yash.c197.experiments.exp06_image_loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SimpleImage(
    model: Any,
    description: String,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(16f / 9f),
    size: Int? = null,
    targetSize: IntSize? = null,
    signature: Long? = null,
) {
    GlideImage(
        model = model,
        contentDescription = description,
        modifier = modifier.clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop
    ) { request ->
        var glideRequest = request
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

        if (targetSize != null) {
            glideRequest = glideRequest.override(targetSize.width, targetSize.height)
        } else if (size != null) {
            glideRequest = glideRequest.override(size, size)
        }

        if (signature != null) {
            glideRequest = glideRequest.signature(ObjectKey(signature))
        }

        glideRequest
    }
}

@Composable
fun SimplePlaceholder(modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
    )
}
