package com.tubes.e_burjo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

//import com.shidiqarifs.edokter.AccountFragment;
import com.tubes.e_burjo.helper.DatabaseHelper;
import com.tubes.e_burjo.fragment.home_fragment;
import com.tubes.e_burjo.fragment.input_fragment;
import com.tubes.e_burjo.R;

import java.util.HashMap;

/**
 * Created by shidiqarifs on 31/10/2017.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    String status ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, home_fragment.newInstance())
                    .commit();



        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                    fragment = home_fragment.newInstance();
                    break;

            case R.id.navigation_account:
                fragment = input_fragment.newInstance();
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();

        return false;
    }

}