package com.example.shutmeproject.Helpers;

import com.example.shutmeproject.Model.AppInfo;

import java.util.Comparator;

public class AppNameComparator implements Comparator<AppInfo> {

    @Override
    public int compare(AppInfo appInfo, AppInfo otherAppInfo) {
        return appInfo.getName().compareToIgnoreCase(otherAppInfo.getName());
    }
}
