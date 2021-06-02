package umn.ac.id.wetix;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.NumberFormat;

public class TheatreFragment extends Fragment implements LocationListener {
    private String admin = "DczDfYT80rbLioDeSvgUYaDIEQg1", uid;
    Location currentLocation;
    private static final int REQUEST_CODE = 101;
    double currLati, currLongi;
    private RecyclerView theatreRV;
    TheatreAdapter adapter;
    ImageButton addTheatre;
    protected LocationManager locationManager;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

        addTheatre = view.findViewById(R.id.addTheatre);

        addTheatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTheatre popUpClass = new AddTheatre();
                popUpClass.showPopupWindow(v, getContext());
            }
        });
        //check admin
        uid = user.getUid();
        if(uid.equals(admin)){
            addTheatre.setVisibility(View.VISIBLE);
        }
        //check permission, just in case
        if (ActivityCompat.checkSelfPermission(
                getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        //fetch lat, long
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        currentLocation = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (currentLocation != null) {
            currLati = (double) currentLocation.getLatitude();
            currLongi = (double) currentLocation.getLongitude();
        }
        //recycler adapter
        Query query = FirebaseDatabase.getInstance().getReference("theatres");
        theatreRV = view.findViewById((R.id.recyclerTheatre));
        theatreRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<TheatreHelper> options = new FirebaseRecyclerOptions.Builder<TheatreHelper>()
                .setQuery(query, TheatreHelper.class)
                .build();
        adapter = new TheatreAdapter(options, currLati, currLongi, getContext());
        theatreRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false
        ));
        theatreRV.setAdapter(adapter);
        return view;

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {}

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