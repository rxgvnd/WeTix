package umn.ac.id.wetix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        loadFragment(new HomeFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void loadFragment(HomeFragment homeFragment) {
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch(item.getItemId()){
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_movie:
                fragment = new MovieFragment();
                break;
            case R.id.navigation_ticket:
                fragment = new TicketFragment();
                break;
        }
        return loadFragment(fragment);
    }
}