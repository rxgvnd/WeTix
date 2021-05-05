package umn.ac.id.wetix;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    FirebaseAuth fAuth;
    FirebaseDatabase root;
    DatabaseReference ref;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fAuth = fAuth.getInstance();
        root = root.getInstance();
        ref = root.getReference("listmovie");

        ImageSlider imageSlider = (ImageSlider) view.findViewById(R.id.slider);
        List<SlideModel> imageList = new ArrayList<>();
        for(int i = 1; i < 6; i++){
            String test = "movie"+i;
            Log.d("TAG", test);
            ref.child(test).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    MovieHelper movie = snapshot.getValue(MovieHelper.class);
                    String link = movie.getPoster();
                    String title = movie.getNamemovie();

                    imageList.add(new SlideModel(link, title, ScaleTypes.CENTER_INSIDE));
                    imageSlider.setImageList(imageList);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
        return view;
    }
}