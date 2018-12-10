package com.dmytro.lisovyi.earthquakemap.ui;

import android.os.Bundle;

import com.dmytro.lisovyi.earthquakemap.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class EarthquakesActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        initViews();
    }

    private void initViews() {
        tabLayout = findViewById(R.id.tabs);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        tabLayout.addOnTabSelectedListener(new BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                int selectedPosition = tab.getPosition();
                selectTab(selectedPosition);
            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });
        selectTab(0);
    }

    private void selectTab(int selectedPosition) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment page;
        if (selectedPosition == 0) {
            page = fragmentManager.findFragmentByTag(ListFragment.class.getSimpleName());
            if (page == null) {
                page = new ListFragment();
            }
        } else {
            page = fragmentManager.findFragmentByTag(MapFragment.class.getSimpleName());
            if (page == null) {
                page = new MapFragment();
            }
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, page, page.getClass().getSimpleName());
        transaction.commit();
    }

}
