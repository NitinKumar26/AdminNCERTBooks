package com.gadgetsfolk.admin.ncertbooks.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadgetsfolk.admin.ncertbooks.NCERTBooksActivity;
import com.gadgetsfolk.admin.ncertbooks.NCERTExemplarSolution;
import com.gadgetsfolk.admin.ncertbooks.NCERTSolutionActivity;
import com.gadgetsfolk.admin.ncertbooks.R;
import com.gadgetsfolk.admin.ncertbooks.adapter.HomeAdapter;
import com.gadgetsfolk.admin.ncertbooks.helper.HelperMethods;
import com.gadgetsfolk.admin.ncertbooks.model.HomeItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    @BindView(R.id.recyclerView_ncert_books)
    RecyclerView recyclerViewNCERTBooks;

    private ArrayList<HomeItem> NCERTBooksList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NCERTBooksList = new ArrayList<>();
        NCERTBooksList.add(new HomeItem(R.drawable.ncert_books, "NCERT New Books"));
        //NCERTBooksList.add(new HomeItem(R.drawable.ic_book, "NCERT Old Books"));
        NCERTBooksList.add(new HomeItem(R.drawable.ncert_solutions, "NCERT Solution"));
        NCERTBooksList.add(new HomeItem(R.drawable.ncert_notes, "NCERT Notes"));
        NCERTBooksList.add(new HomeItem(R.drawable.ncert_exemplar_books, "NCERT Exemplar Books"));
        NCERTBooksList.add(new HomeItem(R.drawable.ncert_exemplar_solution, "NCERT Exemplar Solution"));

        HomeAdapter ncertBooksAdapter = new HomeAdapter(getContext(), NCERTBooksList);
        recyclerViewNCERTBooks.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewNCERTBooks.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        //recyclerViewNCERTBooks.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        recyclerViewNCERTBooks.setAdapter(ncertBooksAdapter);

        recyclerViewNCERTBooks.addOnItemTouchListener(new HelperMethods.RecyclerTouchListener(getContext(), position -> {
            Intent intent;
            switch (position){
                case 0:
                    intent = new Intent(getContext(), NCERTBooksActivity.class);
                    intent.putExtra(getString(R.string.title), NCERTBooksList.get(position).getTitle());
                    intent.putExtra(getString(R.string.type), getString(R.string.new_ncert));
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(getContext(), NCERTSolutionActivity.class);
                    intent.putExtra(getString(R.string.type), getString(R.string.ncert_solutions));
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(getContext(), NCERTSolutionActivity.class);
                    intent.putExtra(getString(R.string.type), getString(R.string.ncert_notes));
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(getContext(), NCERTSolutionActivity.class);
                    intent.putExtra(getString(R.string.type), getString(R.string.ncert_exemplar_books));
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(getContext(), NCERTExemplarSolution.class);
                    startActivity(intent);
                    break;

            }
        }));
    }
}
