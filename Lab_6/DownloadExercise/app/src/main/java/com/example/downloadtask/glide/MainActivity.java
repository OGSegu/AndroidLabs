package com.example.downloadtask.glide;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.downloadtask.R;

public class MainActivity extends AppCompatActivity {

    private final String urlToDownload = "https://malteze.net/images/sampledata/poroda/2017/bernskij-zennenxund_2_163653644.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView);
        Button btnToDownload = findViewById(R.id.btnToDownload);

        btnToDownload.setOnClickListener((btn) -> Glide.with(this).load(urlToDownload).into(imageView));
    }
}