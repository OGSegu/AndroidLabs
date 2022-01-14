package com.example.downloadtask.executor;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.example.downloadtask.R;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

class ImageDownloader implements Runnable {

    private static final String LOG_TAG = "ImageDownloader";

    private final ImageView imageView;
    private final Handler mainHandler;

    private final URL url;

    public ImageDownloader(ImageView imageView, Handler mainHandler, String url) throws MalformedURLException {
        this.imageView = imageView;
        this.mainHandler = mainHandler;
        this.url = new URL(url);
    }

    @Override
    public void run() {
        Log.i("ImageDownloader", "Started downloading image");
        Bitmap result;
        InputStream in = null;
        try {
            in = url.openStream();
        } catch (Exception e) {
            Log.w(LOG_TAG, "Something went wrong with downloading image", e);
        }
        Log.i(LOG_TAG, "Finished downloading image");
        result = in == null ?
                BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_launcher_foreground) :
                BitmapFactory.decodeStream(in);
        mainHandler.post(() -> imageView.setImageBitmap(result));
    }
}