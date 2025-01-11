package com.example.navigationdrawer;

import android.graphics.drawable.Drawable;

public class AppUsageData {

    private String appName;
    private long usageTime;
    private String usageDate;

    public AppUsageData(String appName, long usageTime, String usageDate) {
        this.appName = appName;
        this.usageTime = usageTime;
        this.usageDate = usageDate;
    }

    public String getAppName() {
        return appName;
    }

    public long getUsageTime() {
        return usageTime;
    }

    public String getUsageDate() {
        return usageDate;
    }
}

