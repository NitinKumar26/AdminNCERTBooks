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
import com.gadgetsfolk.admin.ncertbooks.model.Subject;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Subject> subjects;

    public SubjectAdapter(Context mContext, ArrayList<Subject> subjects) {
        this.mContext = mContext;
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_subject, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int serial = position + 1;
        Subject item = subjects.get(position);
        holder.tvSerial.setText(String.valueOf(serial));
        holder.tvTitle.setText(item.getSubject());

        if (position % 5 == 0) holder.rlImg.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_two));
        else if (position % 5 == 1) holder.rlImg.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_three));
        else if (position % 5 == 2) holder.rlImg.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_four));
        else if (position % 5 == 3) holder.rlImg.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_five));
        else holder.rlImg.setBackground(mContext.getResources().getDrawable(R.drawable.gradient));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlImg;
        TextView tvSerial;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlImg = itemView.findViewById(R.id.img);
            tvSerial = itemView.findViewById(R.id.tv_serial);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    public void setItems(ArrayList<Subject> subjects){
        this.subjects = subjects;
    }
}
