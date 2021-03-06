package com.gadgetsfolk.admin.ncertbooks.fragment.ncertbooks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        if (getActivity() != null) type = getActivity().getIntent().getStringExtra(getString(R.string.type));

        Log.e("type", type);

        mBooksList = new ArrayList<>();
        mBooksList.add(new Book(getString(R.string.xii)));
        mBooksList.add(new Book(getString(R.string.xi)));
        mBooksList.add(new Book(getString(R.string.x)));
        mBooksList.add(new Book(getString(R.string.ix)));
        mBooksList.add(new Book(getString(R.string.viii)));
        mBooksList.add(new Book(getString(R.string.vii)));
        mBooksList.add(new Book(getString(R.string.vi)));
        mBooksList.add(new Book(getString(R.string.v)));
        mBooksList.add(new Book(getString(R.string.iv)));
        mBooksList.add(new Book(getString(R.string.iii)));
        mBooksList.add(new Book(getString(R.string.ii)));
        mBooksList.add(new Book(getString(R.string.i)));

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
