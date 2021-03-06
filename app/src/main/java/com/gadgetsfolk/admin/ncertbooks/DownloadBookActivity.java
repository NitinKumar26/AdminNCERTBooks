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


    String link = "https://firebasestorage.googleapis.com/v0/b/tyari-98a5a.appspot.com/o/NCERT%20Books%20%26%20Solutions%20Data%2Fenglish_books%2Fncert_solutions%2Fxii%2Fsociology%2F0vOIl8ZZwsO1acPa1GQi.pdf?alt=media&token=7d14d984-e25d-41cf-975b-9bfdbb09cda7";

    String link2 = "https://firebasestorage.googleapis.com/v0/b/tyari-98a5a.appspot.com/o/NCERT%20Books%20%26%20Solutions%20Data%2Fenglish_books%2Fncert_solutions%2Fxii%2Fsociology%2FCDPzY0miu8MHVKR3Bn3y.pdf?alt=media&token=59b90cef-9e40-49eb-ab61-4499b46a8efd";

    String link3 = "https://firebasestorage.googleapis.com/v0/b/tyari-98a5a.appspot.com/o/NCERT%20Books%20%26%20Solutions%20Data%2Fenglish_books%2Fncert_solutions%2Fxii%2Fsociology%2FGEpW8VLIOpIjOEdsqVmd.pdf?alt=media&token=595801f2-47ef-422d-b514-6df7d326b6b9";

    int startIndex = 163;
    int endIndex = 187;


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
        Log.e("linkLength", String.valueOf(link.length()));
        Log.e("link2Length", String.valueOf(link2.length()));
        Log.e("link3Length", String.valueOf(link3.length()));
        String subString = link.substring(startIndex, endIndex);
        String subString2 = link2.substring(startIndex, endIndex);
        String subString3 = link3.substring(startIndex, endIndex);
        Log.e("substring", subString);
        Log.e("substring2", subString2);
        Log.e("substring3", subString3);
        /*
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
        }
        */
         }

}
