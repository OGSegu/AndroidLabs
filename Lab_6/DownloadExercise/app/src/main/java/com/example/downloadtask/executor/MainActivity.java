package com.example.downloadtask.executor;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.downloadtask.R;

import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    private Handler mainHandler;
    private MyLovelyService service;

    private Future<?> currentDownloadJob;

    private final String urlToDownload = "https://malteze.net/images/sampledata/poroda/2017/bernskij-zennenxund_2_163653644.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mainHandler = new Handler(getBaseContext().getMainLooper());
        this.service = (MyLovelyService) getApplication();

        Button btnToDownload = findViewById(R.id.btnToDownload);
        btnToDownload.setOnClickListener((btn) -> startImageDownloader());
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (currentDownloadJob != null && !currentDownloadJob.isCancelled() && !currentDownloadJob.isDone()) {
            currentDownloadJob.cancel(true);
        }
    }

    private void startImageDownloader() {
        try {
            ImageView imageView = findViewById(R.id.imageView);
            ImageDownloader imageDownloader = new ImageDownloader(imageView, mainHandler, urlToDownload);
            currentDownloadJob = service.downloadAndSetImage(imageDownloader);
        } catch (Exception e) {
            Log.w("ImageDownloader", "Failed to process image downloading", e);
        }
    }
}