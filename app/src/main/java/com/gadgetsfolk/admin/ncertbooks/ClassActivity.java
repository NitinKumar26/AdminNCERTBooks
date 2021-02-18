package com.gadgetsfolk.admin.ncertbooks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadgetsfolk.admin.ncertbooks.adapter.SubjectAdapter;
import com.gadgetsfolk.admin.ncertbooks.helper.HelperMethods;
import com.gadgetsfolk.admin.ncertbooks.model.Subject;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    private SubjectAdapter subjectAdapter;
    private ArrayList<Subject> subjects;
    //private FirebaseFirestore db;
    private String lang;
    private String type;
    private String className;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        ButterKnife.bind(this);
        //db = FirebaseFirestore.getInstance();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        className = getIntent().getStringExtra("class");
        lang = getIntent().getStringExtra("lang") + "_books" ;
        type = getIntent().getStringExtra("type");
        //Log.e("claas", className);
        //Log.e("lang", lang);
        //Log.e("type", type);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.class_name, className));
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        subjects = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        subjectAdapter = new SubjectAdapter(this, subjects);
        recyclerView.setAdapter(subjectAdapter);

        getSubjects();

        recyclerView.addOnItemTouchListener(new HelperMethods.RecyclerTouchListener(this, position -> {
            Intent intent = new Intent(ClassActivity.this, SubjectActivity.class);
            intent.putExtra("class", className);
            intent.putExtra("lang", lang);
            intent.putExtra("type", type);
            intent.putExtra("doc_id", subjects.get(position).getId());
            intent.putExtra("subject_name", subjects.get(position).getSubject());
            startActivity(intent);
        }));

        Log.e("lang", lang);
        Log.e("type", type);
        Log.e("className", className);
    }

    private void getSubjects(){
        FirebaseFirestore.getInstance().collection(lang).document(type)
                .collection("classes")
                .document(className)
                .collection("subjects").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Subject> subjectsList = queryDocumentSnapshots.toObjects(Subject.class);
                    subjects.addAll(subjectsList);
                    subjectAdapter.setItems(subjects);
                    subjectAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }).addOnFailureListener(e -> {})
                .addOnCompleteListener(task -> {});
    }
}
