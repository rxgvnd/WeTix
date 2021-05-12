package umn.ac.id.wetix;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    DatabaseReference ref;
    private RecyclerView movieRV;
    MovieAdapter adapter;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ref = FirebaseDatabase.getInstance().getReference("listmovie");
        Query query = FirebaseDatabase.getInstance().getReference("listmovie");
        movieRV = view.findViewById((R.id.recyclerNowPlaying));
        movieRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<MovieHelper> options = new FirebaseRecyclerOptions.Builder<MovieHelper>()
                .setQuery(query, MovieHelper.class)
                .build();
        adapter = new MovieAdapter(options, getContext());
        movieRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false
        ));
        movieRV.setAdapter(adapter);

        ImageSlider imageSlider = (ImageSlider) view.findViewById(R.id.slider);
        List<SlideModel> imageList = new ArrayList<>();
        for(int i = 1; i < 6; i++){
            String idx = "movie"+i;
            ref.child(idx).addValueEventListener(new ValueEventListener() {
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

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}