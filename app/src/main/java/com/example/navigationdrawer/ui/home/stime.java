package com.example.navigationdrawer.ui.home;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.AppUsageAdapter;
import com.example.navigationdrawer.AppUsageData;
import com.example.navigationdrawer.FirebaseUsageData;
import com.example.navigationdrawer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class stime extends Fragment {
    private RecyclerView recyclerView;
    private List<AppUsageData> appUsageDataList = new ArrayList<>();
    private AppUsageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stime, container, false);

        recyclerView = rootView.findViewById(R.id.recyle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getAppUsageData();
        }

        adapter = new AppUsageAdapter(getContext(), appUsageDataList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }


    private void getAppUsageData() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getContext().getSystemService(Context.USAGE_STATS_SERVICE);
        long currentTime = System.currentTimeMillis();
        long startTime = currentTime - 1000 * 60 * 60 * 24; // Dernières 24 heures

        // Vérifiez si usageStatsManager est nul
        if (usageStatsManager == null) {
            Log.e("FirebaseDebug", "UsageStatsManager is null. Cannot fetch usage data.");
            return;
        }

        // Obtenez les statistiques d'utilisation
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, currentTime);
        if (usageStatsList == null || usageStatsList.isEmpty()) {
            Log.e("FirebaseDebug", "No usage stats found. Check app permissions.");
            return;
        }

        PackageManager packageManager = getActivity().getPackageManager();

        for (UsageStats stats : usageStatsList) {
            try {
                String packageName = stats.getPackageName();
                long totalTimeInForeground = stats.getTotalTimeInForeground();

                // Ignorer les applications avec un temps d'utilisation de 0
                if (totalTimeInForeground == 0) continue;

                String appName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 0)).toString();
                Drawable appIcon = packageManager.getApplicationIcon(packageName);
                String usageDate = android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date(currentTime)).toString();

                // Ajoutez les données localement pour affichage
                AppUsageData data = new AppUsageData(appName, totalTimeInForeground, usageDate);
                appUsageDataList.add(data);

                // Enregistrez les données dans Firebase
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AppUsage");
                String id = databaseReference.push().getKey();

                if (id != null) {
                    FirebaseUsageData firebaseData = new FirebaseUsageData(appName, totalTimeInForeground, usageDate);
                    databaseReference.child(id).setValue(firebaseData)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.d("FirebaseDebug", "Data stored successfully for app: " + appName);
                                } else {
                                    Log.e("FirebaseDebug", "Failed to store data for app: " + appName, task.getException());
                                }
                            });
                } else {
                    Log.e("FirebaseDebug", "Generated ID is null. Data not saved for app: " + appName);
                }

            } catch (PackageManager.NameNotFoundException e) {
                Log.e("FirebaseDebug", "Error getting app details for package: " + stats.getPackageName(), e);
            }
        }

        // Notifiez l'adaptateur pour actualiser l'affichage
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

}
