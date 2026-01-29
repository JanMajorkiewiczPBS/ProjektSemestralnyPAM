package com.example.projektsemestralny.utils

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun saveBitmapToFile(context: Context, bitmap: Bitmap): String? {
    val filename = "${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, filename)
    try {
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        stream.flush()
        stream.close()
        return file.absolutePath
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}
