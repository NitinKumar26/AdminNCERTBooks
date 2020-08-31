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
import com.gadgetsfolk.admin.ncertbooks.model.Chapter;

import java.util.ArrayList;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Chapter> chaptersList;

    public ChapterAdapter(Context mContext, ArrayList<Chapter> chaptersList) {
        this.mContext = mContext;
        this.chaptersList = chaptersList;
    }

    @NonNull
    @Override
    public ChapterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterAdapter.ViewHolder holder, int position) {
        int serial = position + 1;
        Chapter item = chaptersList.get(position);

        holder.tvSerial.setText(String.valueOf(serial));
        holder.tvChapterName.setText(item.getChapter_name());

        if (position % 5 == 0) holder.rlImg.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_two));
        else if (position % 5 == 1) holder.rlImg.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_three));
        else if (position % 5 == 2) holder.rlImg.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_four));
        else if (position % 5 == 3) holder.rlImg.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_five));
        else holder.rlImg.setBackground(mContext.getResources().getDrawable(R.drawable.gradient));
    }

    @Override
    public int getItemCount() {
        return chaptersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlImg;
        private TextView tvSerial;
        private TextView tvChapterName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlImg = itemView.findViewById(R.id.img);
            tvSerial = itemView.findViewById(R.id.tv_serial);
            tvChapterName = itemView.findViewById(R.id.tv_title);
        }
    }

    public void setItems(ArrayList<Chapter> chapters){
        this.chaptersList = chapters;
    }
}
