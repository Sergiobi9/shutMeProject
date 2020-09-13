package com.example.shutmeproject.Helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.shutmeproject.Model.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InstaledApps {

    private static String TAG = "InstaledApps";

    public InstaledApps() {}

    public ArrayList<AppInfo> getInstaledApps(Context context){
        ArrayList<AppInfo> appInfoArrayList = new ArrayList<>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<PackageInfo> packageAppList = context.getPackageManager().getInstalledPackages(0);

        for (PackageInfo packageInfo : packageAppList){
            String name = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            String packageName = packageInfo.packageName;
            Drawable icon = packageInfo.applicationInfo.loadIcon(context.getPackageManager());

            AppInfo appInfo = new AppInfo(name, packageName, icon);
            appInfoArrayList.add(appInfo);
        }

        Log.d(TAG, appInfoArrayList.toString());
        Collections.sort(appInfoArrayList, new AppNameComparator());

        return appInfoArrayList;
    }
}
