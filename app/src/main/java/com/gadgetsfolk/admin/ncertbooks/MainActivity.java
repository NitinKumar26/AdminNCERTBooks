package com.gadgetsfolk.admin.ncertbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.gadgetsfolk.admin.ncertbooks.fragment.HomeFragment;
import com.gadgetsfolk.admin.ncertbooks.fragment.ResultFragment;
import com.gadgetsfolk.admin.ncertbooks.helper.HelperMethods;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private HomeFragment homeFragment;
    //private UpdatesFragment updatesFragment;
    //private TestSeriesFragment testSeriesFragment;
    private ResultFragment resultFragment;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Home");

        navigation.setItemIconTintList(null);

        homeFragment = new HomeFragment();
        //updatesFragment = new UpdatesFragment();
        //testSeriesFragment = new TestSeriesFragment();
        resultFragment = new ResultFragment();

        HelperMethods.INSTANCE.loadFragment(homeFragment, this);
        navigation.setSelectedItemId(R.id.action_home);

        navigation.setOnNavigationItemSelectedListener(listener);
    }

    BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                /*
                case R.id.action_updates:
                    HelperMethods.INSTANCE.showFragment(updatesFragment, MainActivity.this);
                    HelperMethods.INSTANCE.hideFragment(homeFragment, MainActivity.this);
                    HelperMethods.INSTANCE.hideFragment(testSeriesFragment, MainActivity.this);
                    HelperMethods.INSTANCE.hideFragment(resultFragment, MainActivity.this);
                    return true;

                 */
                case R.id.action_home:
                    actionBar.setTitle("Home");
                    //HelperMethods.INSTANCE.hideFragment(updatesFragment, MainActivity.this);
                    HelperMethods.INSTANCE.showFragment(homeFragment, MainActivity.this);
                    //HelperMethods.INSTANCE.hideFragment(testSeriesFragment, MainActivity.this);
                    HelperMethods.INSTANCE.hideFragment(resultFragment, MainActivity.this);
                    return true;
                    /*
                case R.id.action_test_series:
                    HelperMethods.INSTANCE.hideFragment(updatesFragment, MainActivity.this);
                    HelperMethods.INSTANCE.hideFragment(homeFragment, MainActivity.this);
                    HelperMethods.INSTANCE.showFragment(testSeriesFragment, MainActivity.this);
                    HelperMethods.INSTANCE.hideFragment(resultFragment, MainActivity.this);
                    return true;

                     */
                case R.id.action_result:
                    actionBar.setTitle("Result");
                    //HelperMethods.INSTANCE.hideFragment(updatesFragment, MainActivity.this);
                    HelperMethods.INSTANCE.hideFragment(homeFragment, MainActivity.this);
                    //HelperMethods.INSTANCE.hideFragment(testSeriesFragment, MainActivity.this);
                    HelperMethods.INSTANCE.showFragment(resultFragment, MainActivity.this);
                    return true;

            }
            return false;
        }
    };
}