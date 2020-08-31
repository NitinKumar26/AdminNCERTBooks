package com.gadgetsfolk.admin.ncertbooks;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PDFActivity extends AppCompatActivity {
    @BindView(R.id.pdfView)
    PDFView pdfView;
    private String pdfPath;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        title = getIntent().getStringExtra("title");
        if (actionBar != null){
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pdfPath = getIntent().getStringExtra("pdf_path");
        pdfView.fromFile(new File(pdfPath))
                .enableSwipe(true)
                .enableDoubletap(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(4)
                .defaultPage(0)
                .fitEachPage(true)
                .autoSpacing(false)
                .pageSnap(true)
                .pageFling(true)
                .load();
    }
}
