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
import com.gadgetsfolk.admin.ncertbooks.model.HomeItem;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<HomeItem> mHomeItemsList;

    public HomeAdapter(Context mContext, ArrayList<HomeItem> mHomeItemsList) {
        this.mContext = mContext;
        this.mHomeItemsList = mHomeItemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeItem item = mHomeItemsList.get(position);
        holder.tvTitle.setText(item.getTitle());

        holder.tvImage.setText(String.valueOf(item.getTitle().charAt(0)));

        if (position % 5 == 0) holder.rlBackground.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_two));
        else if (position % 5 == 1) holder.rlBackground.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_three));
        else if (position % 5 == 2) holder.rlBackground.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_four));
        else if (position % 5 == 3) holder.rlBackground.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_five));
        else holder.rlBackground.setBackground(mContext.getResources().getDrawable(R.drawable.gradient));
    }

    @Override
    public int getItemCount() {
        return mHomeItemsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlBackground;
        private TextView tvImage;
        private TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlBackground = itemView.findViewById(R.id.img);
            tvImage = itemView.findViewById(R.id.tv_img);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
