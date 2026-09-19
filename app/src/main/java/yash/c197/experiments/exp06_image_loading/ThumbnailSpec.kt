package yash.c197.experiments.exp06_image_loading

import kotlin.math.roundToInt

object ThumbnailSpec {
    const val HEIGHT_DP = 150

    fun heightPx(density: Float): Int {
        require(density > 0f) { "density must be positive, was $density" }
        return (HEIGHT_DP * density).roundToInt().coerceAtLeast(1)
    }

    fun cellWidthPx(gridWidthPx: Int, columns: Int): Int {
        require(gridWidthPx > 0) { "gridWidthPx must be positive, was $gridWidthPx" }
        require(columns > 0) { "columns must be positive, was $columns" }
        return (gridWidthPx / columns).coerceAtLeast(1)
    }
}
