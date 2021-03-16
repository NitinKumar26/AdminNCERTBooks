package com.gadgetsfolk.admin.ncertbooks;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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

        edTvIndexValue = findViewById(R.id.index_value);
        switchIsNegative = findViewById(R.id.isNegative);
        btnUpdatePdfNames = findViewById(R.id.btn_update_pdf_name);

        edTvIndexValue.setText("0");

        switchIsNegative.setOnCheckedChangeListener((buttonView, isChecked) -> isNegative = !isNegative);

        btnUpdatePdfNames.setOnClickListener(v -> {
            indexValue = Integer.parseInt(edTvIndexValue.getText().toString().trim());
            if (indexValue > 0 && isNegative) indexValue = -indexValue;
            Log.e("indexValue", String.valueOf(indexValue));
            Log.e("isNegative", String.valueOf(isNegative));

            updatePdfNames(lang, type, className, indexValue);
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
                            Log.e("urlLength", String.valueOf(url.length()));
                            pdfName = url.substring(startIndex, startIndex + 24);
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
}
