package com.example.navigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppUsageAdapter extends RecyclerView.Adapter<AppUsageAdapter.ViewHolder> {

    private Context context;
    private List<AppUsageData> appUsageList;

    public AppUsageAdapter(Context context, List<AppUsageData> appUsageList) {
        this.context = context;
        this.appUsageList = appUsageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppUsageData data = appUsageList.get(position);
        holder.appName.setText(data.getAppName());
        holder.appUsageTime.setText("Usage Time: " + formatUsageTime(data.getUsageTime()));
        holder.appDate.setText("Date: " + data.getUsageDate());
    }

    @Override
    public int getItemCount() {
        return appUsageList.size();
    }

    private String formatUsageTime(long time) {
        long seconds = time / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if (hours > 0) {
            return hours + "h " + (minutes % 60) + "m";
        } else if (minutes > 0) {
            return minutes + "m " + (seconds % 60) + "s";
        } else {
            return seconds + "s";
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appName, appUsageTime, appDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appName = itemView.findViewById(R.id.appName);
            appUsageTime = itemView.findViewById(R.id.appUsageTime);
            appDate = itemView.findViewById(R.id.appDate);
        }
    }
}
