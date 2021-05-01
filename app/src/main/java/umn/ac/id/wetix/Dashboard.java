package umn.ac.id.wetix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    ImageButton toProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ImageSlider imageSlider = findViewById(R.id.slider);

         List<SlideModel> slideModels = new ArrayList<>();
         slideModels.add(new SlideModel("https://i.pinimg.com/236x/62/c4/bf/62c4bf742aa0ad91c8541da187f20486.jpg"));
         slideModels.add(new SlideModel("https://cdn.tmpo.co/data/2018/09/23/id_735800/735800_720.jpg"));
         slideModels.add(new SlideModel("https://cdn1-production-images-kly.akamaized.net/hcPpUU1eH_KBhJirNmYGzHwGXMw=/640x853/smart/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/3301804/original/011403400_1605845389-Asih_2.jpg"));
         slideModels.add(new SlideModel("https://cinemags.co.id/wp-content/uploads/2019/08/Spidey-poster.jpg"));
         imageSlider.setImageList(slideModels, true);


        toProfile = findViewById(R.id.toProfile);
        loadFragment(new HomeFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, ProfileActivity.class));
            }
        });
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