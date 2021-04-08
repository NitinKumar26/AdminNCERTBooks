package com.gadgetsfolk.admin.ncertbooks;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kotlinpermissions.KotlinPermissions;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class UpdateDocs extends AppCompatActivity {
    private String lang;
    private String type;
    private String className;
    private String docId;
    private String subjectName;
    int startIndex = 165;
    String pdfName;
    String url;
    private EditText edTvIndexValue;
    private SwitchCompat switchIsNegative;
    private Button btnUpdatePdfNames;
    private Button btnUpdateDocIds;
    private Button btnAddSubjectsInStorage;
    private boolean isNegative = false;
    int indexValue;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_docs);

        className = getIntent().getStringExtra(getString(R.string.user_class));
        lang = getIntent().getStringExtra(getString(R.string.lang));
        type = getIntent().getStringExtra(getString(R.string.type));
        docId = getIntent().getStringExtra(getString(R.string.doc_id));
        subjectName = getIntent().getStringExtra(getString(R.string.subject_name));

        Log.e("className", className);
        Log.e("lang", lang);
        Log.e("type", type);
        Log.e("docId", docId);
        Log.e("subjectName", subjectName);

        edTvIndexValue = findViewById(R.id.index_value);
        switchIsNegative = findViewById(R.id.isNegative);
        btnUpdatePdfNames = findViewById(R.id.btn_update_pdf_name);
        btnUpdateDocIds = findViewById(R.id.btn_doc_update_ids);
        btnAddSubjectsInStorage = findViewById(R.id.btn_add_subjects);

        edTvIndexValue.setText("0");

        switchIsNegative.setOnCheckedChangeListener((buttonView, isChecked) -> isNegative = !isNegative);

        btnUpdatePdfNames.setOnClickListener(v -> {
            indexValue = Integer.parseInt(edTvIndexValue.getText().toString().trim());
            if (indexValue > 0 && isNegative) indexValue = -indexValue;
            Log.e("indexValue", String.valueOf(indexValue));
            Log.e("isNegative", String.valueOf(isNegative));

            updatePdfNames(lang, type, className, indexValue);
        });


        btnUpdateDocIds.setOnClickListener(v -> updateDocIds(lang, type, className, docId));

        btnAddSubjectsInStorage.setOnClickListener(v -> addSubjectsInStorage(lang, type, className, docId));
    }

    @Override
    protected void onStart() {
        super.onStart();
        KotlinPermissions.with(this).permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .onAccepted(list ->{
                    Log.d("Permission:", "Read Permission Granted");
                }).ask();
    }

    private void updateDocIds(String lang, String type, String className, String docId){
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
                            FirebaseFirestore.getInstance().collection(lang)
                                    .document(type)
                                    .collection("classes")
                                    .document(className)
                                    .collection("subjects")
                                    .document(docId)
                                    .collection("chapters").document(queryDocumentSnapshots.getDocuments().get(i).getId())
                                    .update("id", queryDocumentSnapshots.getDocuments().get(i).getId())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(UpdateDocs.this, "Oh Yeah!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
    }

    private void updatePdfNames(String lang, String type, String className, int index){
        startIndex = startIndex + index;
        FirebaseFirestore.getInstance().collection(lang).document(type)
                .collection("classes")
                .document(className)
                .collection("subjects")
                .document(docId)
                .collection("chapters").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++){
                        url = queryDocumentSnapshots.getDocuments().get(i).getString("chapter_pdf_url");
                        if (url != null) {
                            Log.e("urlLength " + i, String.valueOf(url.length()));
                            pdfName = url.split("\\?", 24)[0];
                            pdfName = pdfName.substring(pdfName.length() - 24);
                            //pdfName = url.substring(startIndex, startIndex + 24);
                            FirebaseFirestore.getInstance()
                                    .collection(lang)
                                    .document(type)
                                    .collection("classes")
                                    .document(className)
                                    .collection("subjects")
                                    .document(docId)
                                    .collection("chapters").document(queryDocumentSnapshots.getDocuments().get(i).getId())
                                    .update("pdf_name", pdfName).addOnSuccessListener(unused ->
                                    Toast.makeText(UpdateDocs.this, "Oh Yeah!", Toast.LENGTH_SHORT).show());
                        }
                    }
                }).addOnFailureListener(e -> Log.e("exception", e.getMessage()))
                .addOnCompleteListener(task -> {});
    }

    private void addSubjectsInStorage(String lang, String type, String className, String docId){
        FirebaseFirestore.getInstance().collection(lang).document(type)
                .collection("classes")
                .document(className)
                .collection("subjects").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Uri file = Uri.fromFile(new File("/storage/emulated/0/InstaSave/InstaDownload/comPopularAppInstaget.txt"));
                for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++){
                    StorageReference txtFileRef = FirebaseStorage.getInstance().getReference().child(lang + "/" + type + "/" +
                            className.toLowerCase() + "/" + queryDocumentSnapshots.getDocuments().get(i).getString("subject") + "/" + file.getLastPathSegment());
                    //Register observers to listen for when the upload is done or if it fails
                    UploadTask uploadTask = txtFileRef.putFile(file);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UpdateDocs.this, "Yeah!! -- Storage", Toast.LENGTH_SHORT).show(); }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Log.e("failure", e.getMessage()); }
                    });
                }
            }
        });
    }
}
