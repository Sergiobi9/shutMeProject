package com.example.shutmeproject.Helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.shutmeproject.Model.AppInfo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InstaledApps {

    private static String TAG = "InstaledApps";
    private SharedPreferences sharedPreferences;

    public InstaledApps() {}

    public ArrayList<AppInfo> getInstaledApps(Context context){
        sharedPreferences = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        ArrayList<AppInfo> appInfoArrayList = new ArrayList<>();


        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final List<PackageInfo> packageAppList = context.getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        String[] packagesArrayList = new String[packageAppList.size()];

        for (int i = 0; i < packageAppList.size(); i++){
            PackageInfo packageInfo = packageAppList.get(i);

            if (packageInfo.requestedPermissions != null){
                for (String permission : packageInfo.requestedPermissions) {
                    if (TextUtils.equals(permission, android.Manifest.permission.INTERNET)) {
                        String name = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                        String packageName = packageInfo.packageName;
                        Drawable icon = packageInfo.applicationInfo.loadIcon(context.getPackageManager());

                        AppInfo appInfo = new AppInfo(name, packageName, icon);
                        packagesArrayList[i] = packageName;
                        appInfoArrayList.add(appInfo);
                        break;
                    }
                }
            }
        }

        Log.d(TAG, appInfoArrayList.toString());
        Collections.sort(appInfoArrayList, new AppNameComparator());

        savePackageNameArrayList(packagesArrayList);

        return appInfoArrayList;
    }

    private void savePackageNameArrayList(String[] packagesArrayList){
        Gson gson = new Gson();
        String json = gson.toJson(packagesArrayList);
        sharedPreferences.edit().putString("appsPackageNameArrayList", json).apply();
    }
}
