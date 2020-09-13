package com.example.shutmeproject.Helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Permissions {

    private final int REQUEST_MODIFY_PHONE_STATE = 1;

    public Permissions() {

    }

    public boolean checkModifyPhoneStatePermission(Context context){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.MODIFY_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED;
    }

    public void askModifyPhoneStatePermission(Activity activity){
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.MODIFY_PHONE_STATE
        }, REQUEST_MODIFY_PHONE_STATE);
    }
}
