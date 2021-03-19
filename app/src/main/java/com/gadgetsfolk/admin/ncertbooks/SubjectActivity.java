package com.gadgetsfolk.admin.ncertbooks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadgetsfolk.admin.ncertbooks.adapter.ChapterAdapter;
import com.gadgetsfolk.admin.ncertbooks.model.Chapter;
import com.google.firebase.firestore.FirebaseFirestore;

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
    }

    private void getChapters(String docId){
        FirebaseFirestore.getInstance().collection(lang).document(type)
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_update:
                Intent intent = new Intent(this, UpdateDocs.class);
                intent.putExtra("class", className);
                intent.putExtra("lang", lang);
                intent.putExtra("type", type);
                intent.putExtra("doc_id", docId);
                intent.putExtra("subject_name", subjectName);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
