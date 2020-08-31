package com.gadgetsfolk.admin.ncertbooks;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadgetsfolk.admin.ncertbooks.adapter.ChapterAdapter;
import com.gadgetsfolk.admin.ncertbooks.helper.HelperMethods;
import com.gadgetsfolk.admin.ncertbooks.model.Chapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kotlinpermissions.KotlinPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    private ChapterAdapter chapterAdapter;
    private ArrayList<Chapter> chapters;
    private FirebaseFirestore db;
    private String lang;
    private String type;
    private String className;
    private String docId;
    private String subjectName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        ButterKnife.bind(this);
        db = FirebaseFirestore.getInstance();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        className = getIntent().getStringExtra("class");
        lang = getIntent().getStringExtra("lang");
        type = getIntent().getStringExtra("type");
        docId = getIntent().getStringExtra("doc_id");
        subjectName = getIntent().getStringExtra("subject_name");
        //Log.e("claas", className);
        //Log.e("lang", lang);
        //Log.e("type", type);
        //Log.e("doc_id", docId);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(subjectName);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chapters = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        chapterAdapter = new ChapterAdapter(this, chapters);
        recyclerView.setAdapter(chapterAdapter);

        getChapters(docId);

        recyclerView.addOnItemTouchListener(new HelperMethods.RecyclerTouchListener(this, position -> {
            KotlinPermissions.with(this)
                    .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .onAccepted(list -> {
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GadNCERT/" +
                                chapters.get(position).getPdf_name();
                        File file = new File(path);
                        if (file.exists()){
                            Intent intent = new Intent(SubjectActivity.this, PDFActivity.class);
                            intent.putExtra("title", chapters.get(position).getChapter_name());
                            intent.putExtra("pdf_path", path);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(SubjectActivity.this, DownloadBookActivity.class);
                            intent.putExtra("title", chapters.get(position).getChapter_name());
                            intent.putExtra("chapter_pdf_url", chapters.get(position).getChapter_pdf_url());
                            startActivity(intent);
                        }
                    }).ask();
        }));
    }

    private void getChapters(String docId){
        db.collection(lang).document(type)
                .collection("classes")
                .document(className)
                .collection("subjects")
                .document(docId)
                .collection("chapters").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Chapter> subjectsList = queryDocumentSnapshots.toObjects(Chapter.class);
                    chapters.addAll(subjectsList);
                    chapterAdapter.setItems(chapters);
                    chapterAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }).addOnFailureListener(e -> {})
                .addOnCompleteListener(task -> {});
    }
}
