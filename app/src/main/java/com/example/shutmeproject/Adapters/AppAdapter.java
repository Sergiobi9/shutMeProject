package com.example.shutmeproject.Adapters;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shutmeproject.Model.AppInfo;
import com.example.shutmeproject.R;

import java.util.ArrayList;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder>  {

    private ArrayList<AppInfo> list;
    private OnMyAppListener onMyAppListener;
    private Context context;

    public AppAdapter(ArrayList<AppInfo> list, OnMyAppListener onMyAppListener, Context context) {
        this.list = list;
        this.onMyAppListener = onMyAppListener;
        this.context = context;
    }

    @NonNull
    @Override
    public AppAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.app_info_list, viewGroup,false);

        AppAdapter.ViewHolder vh = new AppAdapter.ViewHolder(v, onMyAppListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AppAdapter.ViewHolder viewHolder, int position) {
        AppInfo appInfo = list.get(position);

        viewHolder.name.setText(appInfo.getName());
        viewHolder.packageName.setText(appInfo.getPackageName());
        viewHolder.icon.setImageDrawable(appInfo.getIcon());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView packageName;
        public ImageView icon;
        public AppAdapter.OnMyAppListener onMyAppListener;

        public ViewHolder(@NonNull View itemView, AppAdapter.OnMyAppListener onMyAppListener){
            super(itemView);
            name = itemView.findViewById(R.id.app_name);
            name.setTypeface(null, Typeface.BOLD);

            packageName = itemView.findViewById(R.id.package_name);
            icon = itemView.findViewById(R.id.app_icon);
            this.onMyAppListener = onMyAppListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onMyAppListener.onMyAppClicked(getAdapterPosition());
        }
    }

    public interface OnMyAppListener {
        void onMyAppClicked(int position);
    }
}
