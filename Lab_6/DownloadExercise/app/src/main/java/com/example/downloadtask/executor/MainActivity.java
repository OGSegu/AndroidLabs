package com.example.downloadtask.executor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.downloadtask.R;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private Handler mainHandler;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    // В данном кейсе идея говори, что можно сделать локальной, тк ImageDownloader подкласс в нашем активити (для удобства).
    private final String urlToDownload = "https://malteze.net/images/sampledata/poroda/2017/bernskij-zennenxund_2_163653644.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainHandler = new Handler(getBaseContext().getMainLooper());

        Button btnToDownload = findViewById(R.id.btnToDownload);
        btnToDownload.setOnClickListener((btn) -> startImageDownloader());
    }

    @Override
    protected void onStop() {

        super.onStop();
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                Log.w("ImageDownloader", "Can't stop executor service");
                finishAndRemoveTask();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void startImageDownloader() {
        try {
            ImageDownloader imageDownloader = new ImageDownloader(urlToDownload);
            executorService.execute(imageDownloader);
        } catch (Exception e) {
            Log.w("ImageDownloader", "Failed to process image downloading", e);
        }
    }

    private class ImageDownloader implements Runnable {
        public final URL url;

        public ImageDownloader(String url) throws MalformedURLException {
            this.url = new URL(url);
        }

        @Override
        public void run() {
            Log.i("ImageDownloader", "Starting downloading image");
            ImageView imageView = findViewById(R.id.imageView);
            try {
                InputStream in = url.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                mainHandler.post(() -> imageView.setImageBitmap(bitmap));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("ImageDownloader", "Finished downloading image");
        }
    }
}