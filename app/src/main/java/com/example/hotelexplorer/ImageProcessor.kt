package com.example.hotelexplorer

import android.graphics.Bitmap
import android.graphics.Canvas

object ImageProcessor {
    fun reduceEdge(src: Bitmap): Bitmap {
        return Bitmap.createBitmap(src, 1, 1, src.width - 2, src.height - 2)
    }
}