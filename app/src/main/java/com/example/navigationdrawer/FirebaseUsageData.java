package com.example.navigationdrawer;

public class FirebaseUsageData {
    private String appName;
    private long usageTime;
    private String usageDate;

    public FirebaseUsageData() {
    }

    public FirebaseUsageData(String appName, long usageTime, String usageDate) {
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
