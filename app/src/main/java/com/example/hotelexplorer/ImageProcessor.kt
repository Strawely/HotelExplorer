package com.example.hotelexplorer

import android.graphics.Bitmap

object ImageProcessor {
    fun reduceEdge(bitmap: Bitmap?): Bitmap {
        val src = bitmap ?: Bitmap.createBitmap(100, 100, Bitmap.Config.ALPHA_8)
        return Bitmap.createBitmap(src, 1, 1, src.width - 2, src.height - 2)
    }
}