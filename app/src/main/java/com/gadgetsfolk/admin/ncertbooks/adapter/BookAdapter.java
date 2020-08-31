package com.gadgetsfolk.admin.ncertbooks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadgetsfolk.admin.ncertbooks.R;
import com.gadgetsfolk.admin.ncertbooks.model.Book;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private ArrayList<Book> mBooksList;
    private Context mContext;

    public BookAdapter(ArrayList<Book> mBooksList, Context mContext) {
        this.mBooksList = mBooksList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        Book item = mBooksList.get(position);
        holder.tvClass.setText(mContext.getString(R.string.class_name, item.getClass_name()));
        holder.tvImage.setText(item.getClass_name());

        if (position % 5 == 0) holder.rlBackGround.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_two));
        else if (position % 5 == 1) holder.rlBackGround.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_three));
        else if (position % 5 == 2) holder.rlBackGround.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_four));
        else if (position % 5 == 3) holder.rlBackGround.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_five));
        else holder.rlBackGround.setBackground(mContext.getResources().getDrawable(R.drawable.gradient));
    }

    @Override
    public int getItemCount() {
        return mBooksList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClass, tvImage;
        private RelativeLayout rlBackGround;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClass = itemView.findViewById(R.id.tv_class);
            tvImage = itemView.findViewById(R.id.tv_img);
            rlBackGround = itemView.findViewById(R.id.rel_back);
        }
    }
}
