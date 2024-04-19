package com.app.assignment.helpers

import android.graphics.Bitmap
import com.app.assignment.R
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ImageLoader (private val imageView: ImageView) {

    fun loadImage(urlString: String) {
        val task = ImageLoadingTask(imageView)
        task.execute(urlString)
    }

    private class ImageLoadingTask(private val imageView: ImageView) :
        AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg params: String): Bitmap? {
            val urlString = params[0]
            var connection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            try {
                val url = URL(urlString)
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                inputStream = connection.inputStream
                return BitmapFactory.decodeStream(inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
                inputStream?.close()
            }
            return null
        }

        override fun onPostExecute(result: Bitmap?) {
            result?.let {
                imageView.setImageBitmap(result)
            } ?: run {
                // Handle error or set placeholder image
                imageView.setImageResource(R.drawable.image_placeholder)
            }
        }
    }
}