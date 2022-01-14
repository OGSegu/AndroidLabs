package com.example.downloadtask.executor;

import android.app.Application;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MyLovelyService extends Application {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Future<?> downloadAndSetImage(ImageDownloader imageDownloader) {
       return executorService.submit(imageDownloader);
    }

    public void shutdown() {
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                Log.w("ImageDownloader", "Can't stop executor service");
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
