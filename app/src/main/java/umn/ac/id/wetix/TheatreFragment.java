package umn.ac.id.wetix;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.NumberFormat;

public class TheatreFragment extends Fragment {
    private String admin = "DczDfYT80rbLioDeSvgUYaDIEQg1", uid, jarak;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    String currLat, currLong;
    double currLati, currLongi;
    private RecyclerView theatreRV;
    TheatreAdapter adapter;
    ImageButton addTheatre;
    TextView curlat, curlong;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference ref=  FirebaseDatabase.getInstance().getReference("theatres");

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
                popUpClass.showPopupWindow(v);
            }
        });

        uid = user.getUid();
        if(uid.equals(admin)){
            addTheatre.setVisibility(View.VISIBLE);
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        curlat = view.findViewById(R.id.curlat);
        curlong = view.findViewById(R.id.curlong);
        fetchLocation();
//        setLoc();

        Query query = FirebaseDatabase.getInstance().getReference("theatres");
        theatreRV = view.findViewById((R.id.recyclerTheatre));
        theatreRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<TheatreHelper> options = new FirebaseRecyclerOptions.Builder<TheatreHelper>()
                .setQuery(query, TheatreHelper.class)
                .build();
        Log.d("currloc", currLat + ", " + currLong);
//        adapter = new TheatreAdapter(options, currLati, currLongi);
        theatreRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false
        ));
        theatreRV.setAdapter(adapter);
                return view;

    }

    private void setLoc() {
        currLat = curlat.getText().toString().trim();
        currLong = curlong.getText().toString().trim();
        currLati = Double.parseDouble(currLat);
        currLongi =Double.parseDouble(currLong);
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getActivity().getApplicationContext(), currentLocation.getLatitude() + ", " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    NumberFormat nm = NumberFormat.getNumberInstance();
                    curlat.setText(nm.format(currentLocation.getLatitude()));
                    curlong.setText(nm.format(currentLocation.getLongitude()));
                }
            }
        });
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