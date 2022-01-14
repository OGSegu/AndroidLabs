package com.example.downloadtask.coroutine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.downloadtask.R
import kotlinx.coroutines.*
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val urlToDownload =
        "https://malteze.net/images/sampledata/poroda/2017/bernskij-zennenxund_2_163653644.jpg"
    private lateinit var currentDownloadJob: Job;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnToDownload = findViewById<Button>(R.id.btnToDownload)
        val imageView = findViewById<ImageView>(R.id.imageView)

        btnToDownload.setOnClickListener {
            currentDownloadJob = CoroutineScope(Dispatchers.IO).launch {
                val bitmap = startImageDownloader(urlToDownload)
                withContext(Dispatchers.Main){
                    imageView.setImageBitmap(bitmap)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (this::currentDownloadJob.isInitialized) {
            currentDownloadJob.cancel();
        }
    }

    private fun startImageDownloader(urlStr: String): Bitmap? {
        Log.i("ImageDownloader", "Started downloading image")
        val url = URL(urlStr);
        val `in` = url.openStream()
        val bitmap = BitmapFactory.decodeStream(`in`)
        Log.i("ImageDownloader", "Finished downloading image")
        return bitmap;
    }
}
