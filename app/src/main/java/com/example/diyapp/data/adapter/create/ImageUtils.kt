package com.example.diyapp.data.adapter.create

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

object ImageUtils {

    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }

    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

//    // Save Bitmap to a temporary file
//    fun saveBitmapToFile(context: Context, bitmap: Bitmap): String {
//        val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
//        try {
//            FileOutputStream(file).use { bitmap.compress(Bitmap.CompressFormat.JPEG, 80, it) }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        return file.absolutePath
//    }
//
//    // Load Bitmap from a file path
//    fun loadBitmapFromFile(filePath: String): Bitmap? {
//        return BitmapFactory.decodeFile(filePath)
//    }
//
//    // Convert a list of Uris to a list of Base64 Strings
//    fun urisToBase64List(context: Context, uris: List<Uri>): List<String> {
//        return uris.mapNotNull { uri ->
//            uriToBitmap(context, uri)?.let { bitmapToBase64(it) }
//        }
//    }
//
//    // Convert a list of Uris to temporary file paths
//    fun urisToFilePaths(context: Context, uris: List<Uri>): List<String> {
//        return uris.mapNotNull { uri ->
//            uriToBitmap(context, uri)?.let { saveBitmapToFile(context, it) }
//        }
//    }
}