package com.gadgetsfolk.admin.ncertbooks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadBookActivity extends AppCompatActivity {
    private String pdf_url;
    private String title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_book);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(R.string.download_book);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        PRDownloader.initialize(getApplicationContext());

        pdf_url = getIntent().getStringExtra("chapter_pdf_url");
        title = getIntent().getStringExtra("title");
    }

    @OnClick(R.id.btn_download_book)
    void downloadBook(){
        File myDirectory = new File(Environment.getExternalStorageDirectory(), "GadNCERT");
        if (!myDirectory.exists()){
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GadNCERT";
            myDirectory.mkdirs();
            String fileName = pdf_url
                    .substring(pdf_url.lastIndexOf("/") + 1);
            //Log.e("fileName", fileName);
            PRDownloader.download(pdf_url, path, fileName)
                    .build()
                    .setOnStartOrResumeListener(() -> {
                        Log.e("status", "started");

                    }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    Log.e("status", "complete");
                    Intent intent = new Intent(DownloadBookActivity.this, PDFActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("pdf_path", path + "/" + fileName);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(Error error) {
                    Log.e("error", error.toString());

                }
            });
        }
        else{
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GadNCERT";
            String fileName = pdf_url
                    .substring(pdf_url.lastIndexOf("/") + 1);
            Log.e("fileName", fileName);
            PRDownloader.download(pdf_url, path, fileName)
                    .build()
                    .setOnStartOrResumeListener(() -> {
                        Log.e("status", "started");

                    }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    Log.e("status", "complete");
                    Intent intent = new Intent(DownloadBookActivity.this, PDFActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("pdf_path", path + "/" + fileName);
                    startActivity(intent);
                    finish();
                }
                @Override
                public void onError(Error error) {
                    Log.e("status","error");
                }
            });
        } }
}
