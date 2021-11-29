package com.example.downloadtask.coroutine

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.downloadtask.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var mainHandler: Handler? = null

    // В данном кейсе идея говори, что можно сделать локальной, тк ImageDownloader подкласс в нашем активити (для удобства).
    private val urlToDownload =
        "https://malteze.net/images/sampledata/poroda/2017/bernskij-zennenxund_2_163653644.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainHandler = Handler(baseContext.mainLooper)

        val btnToDownload = findViewById<Button>(R.id.btnToDownload)
        btnToDownload.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runCatching {
                    startImageDownloader(urlToDownload)
                }
            }
        }
    }

    private fun startImageDownloader(urlStr: String) {
        Log.i("ImageDownloader", "Starting downloading image")
        val imageView = findViewById<ImageView>(R.id.imageView)
        val url = URL(urlStr);
        val `in` = url.openStream()
        val bitmap = BitmapFactory.decodeStream(`in`)
        mainHandler!!.post { imageView.setImageBitmap(bitmap) }
        Log.i("ImageDownloader", "Finished downloading image")
    }
}
