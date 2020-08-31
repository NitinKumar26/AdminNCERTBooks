package com.gadgetsfolk.admin.ncertbooks.fragment.ncertbooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadgetsfolk.admin.ncertbooks.ClassActivity;
import com.gadgetsfolk.admin.ncertbooks.R;
import com.gadgetsfolk.admin.ncertbooks.adapter.BookAdapter;
import com.gadgetsfolk.admin.ncertbooks.helper.HelperMethods;
import com.gadgetsfolk.admin.ncertbooks.model.Book;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnglishFragment extends Fragment {
    private ArrayList<Book> mBooksList;
    @BindView(R.id.recyclerView_books)
    RecyclerView recyclerView;
    String type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_english, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        type = getActivity().getIntent().getStringExtra("type");


        mBooksList = new ArrayList<>();
        mBooksList.add(new Book("XII"));
        mBooksList.add(new Book("XI"));
        mBooksList.add(new Book("X"));
        mBooksList.add(new Book("IX"));
        mBooksList.add(new Book("VIII"));
        mBooksList.add(new Book("VII"));
        mBooksList.add(new Book("VI"));
        mBooksList.add(new Book("V"));
        mBooksList.add(new Book("IV"));
        mBooksList.add(new Book("III"));
        mBooksList.add(new Book("II"));
        mBooksList.add(new Book("I"));

        BookAdapter adapter = new BookAdapter(mBooksList, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new HelperMethods.RecyclerTouchListener(getContext(), position -> {
            Intent intent = new Intent(getContext(), ClassActivity.class);
            intent.putExtra("lang", "english");
            intent.putExtra("type", type);
            intent.putExtra("class", mBooksList.get(position).getClass_name());
            startActivity(intent);
        }));
    }
}
