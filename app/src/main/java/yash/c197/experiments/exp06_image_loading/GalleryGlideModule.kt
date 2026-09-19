package yash.c197.experiments.exp06_image_loading

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruArrayPool
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class GalleryGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val calculator = MemorySizeCalculator.Builder(context)
            .setBitmapPoolScreens(BITMAP_POOL_SCREENS)
            .setMemoryCacheScreens(MEMORY_CACHE_SCREENS)
            .build()

        builder.setBitmapPool(LruBitmapPool(calculator.bitmapPoolSize.toLong()))
        builder.setArrayPool(LruArrayPool(calculator.arrayPoolSizeInBytes))
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
        builder.setDiskCache(
            InternalCacheDiskCacheFactory(context, DISK_CACHE_DIR, DISK_CACHE_SIZE_BYTES)
        )
        builder.setDefaultRequestOptions(
            RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .disallowHardwareConfig()
        )
    }

    override fun isManifestParsingEnabled(): Boolean = false

    companion object {
        private const val BITMAP_POOL_SCREENS = 4f
        private const val MEMORY_CACHE_SCREENS = 2f
        private const val DISK_CACHE_DIR = "glide_image_cache"
        private const val DISK_CACHE_SIZE_BYTES = 100L * 1024 * 1024
    }
}
