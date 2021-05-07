package umn.ac.id.wetix;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.content.ContextWrapper;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.IOException;

public class TheatreFragment extends Fragment {
    private RecyclerView theatreRV;
    TheatreAdapter adapter;
    ImageButton addTheatre;
    private final int PICK_IMAGE_REQUEST = 22;
    Uri filePath, theatrePict;

    public TheatreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theatre, container, false);

        Query query = FirebaseDatabase.getInstance().getReference("theatres");
        theatreRV = view.findViewById((R.id.recyclerTheatre));
        theatreRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<TheatreHelper> options = new FirebaseRecyclerOptions.Builder<TheatreHelper>()
                .setQuery(query, TheatreHelper.class)
                .build();
        adapter = new TheatreAdapter(options);
        theatreRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false
        ));
        theatreRV.setAdapter(adapter);

        addTheatre = view.findViewById(R.id.addTheatre);
        addTheatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTheatre popUpClass = new AddTheatre();
                popUpClass.showPopupWindow(v);
            }
        });

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