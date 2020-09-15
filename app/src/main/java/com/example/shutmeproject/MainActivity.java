package com.example.shutmeproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.shutmeproject.Fragments.AppListFragment;
import com.example.shutmeproject.Fragments.Schedule.AddScheduleFragment;
import com.example.shutmeproject.Fragments.Schedule.ScheduleMenuFragment;
import com.example.shutmeproject.Fragments.Settings.SettingsFragment;
import com.example.shutmeproject.Fragments.ShopFragment;
import com.example.shutmeproject.Fragments.VPNFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private static final String TAG = "MainActivityLOG";
    static MainActivity instance;

    public BottomNavigationView bottomNavigationMenu;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return doSelectTabIndex(item.getItemId());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        instance = this;
        context = getApplicationContext();

        initView();
    }

    private void initView(){
        bottomNavigationMenu = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationMenu.setSelectedItemId(R.id.navigation_03);

        bottomNavigationMenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        doSelectTabIndex(R.id.navigation_03);
    }

    private boolean doSelectTabIndex(int index){
        boolean toReturn = true;

        switch (index) {
            case R.id.navigation_01:
                this.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ScheduleMenuFragment()).addToBackStack(null).commit();
                break;
            case R.id.navigation_02:
                this.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new AppListFragment()).addToBackStack(null).commit();
                break;
            case R.id.navigation_03:
                this.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new VPNFragment()).addToBackStack(null).commit();
                break;
            case R.id.navigation_04:
                this.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ShopFragment()).addToBackStack(null).commit();
                break;
            case R.id.navigation_05:
                this.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new SettingsFragment()).addToBackStack(null).commit();
                break;
            default:
                toReturn = false;
                break;
        }
        return toReturn;
    }

    public static MainActivity getInstance() {
        return instance;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}