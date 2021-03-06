package com.gadgetsfolk.admin.ncertbooks;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UpdateDocs extends AppCompatActivity {
    private String lang;
    private String type;
    private String className;
    private String docId;
    private String subjectName;
    int startIndex = 162;
    int endIndex = 186;
    String pdfName;
    String url;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_docs);

        className = getIntent().getStringExtra(getString(R.string.user_class));
        lang = getIntent().getStringExtra(getString(R.string.lang));
        type = getIntent().getStringExtra(getString(R.string.type));
        docId = getIntent().getStringExtra(getString(R.string.doc_id));
        subjectName = getIntent().getStringExtra(getString(R.string.subject_name));

        updatePdfNames(lang, type, className);
    }


    private void updatePdfNames(String lang, String type, String className){
        FirebaseFirestore.getInstance().collection(lang).document(type)
                .collection("classes")
                .document(className)
                .collection("subjects")
                .document(docId)
                .collection("chapters").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++){
                            url = queryDocumentSnapshots.getDocuments().get(i).getString("chapter_pdf_url");
                            if (url != null) {
                                Log.e("urlLength", String.valueOf(url.length()));
                                pdfName = url.substring(startIndex, endIndex);
                                FirebaseFirestore.getInstance().collection(lang).document(type)
                                        .collection("classes")
                                        .document(className)
                                        .collection("subjects")
                                        .document(docId)
                                        .collection("chapters").document(queryDocumentSnapshots.getDocuments().get(i).getId())
                                        .update("pdf_name", pdfName).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(UpdateDocs.this, "Oh Yeah!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                }).addOnFailureListener(e -> {})
                .addOnCompleteListener(task -> {});
    }
}
