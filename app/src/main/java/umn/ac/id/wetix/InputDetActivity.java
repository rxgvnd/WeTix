package umn.ac.id.wetix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class InputDetActivity extends AppCompatActivity {
    Spinner spinnerThtr, spinnerTime, spinnerSeat;
    Button sbmtBtn;
    DatabaseReference ref;
    private String retMovie;
    private List thtrs = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_det);
        ref = FirebaseDatabase.getInstance().getReference("theatres");
        retMovie = getIntent().getExtras().get("movie_idx").toString();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot thtrSnapshot: snapshot.getChildren()) {
                    String nmBioskop = thtrSnapshot.child("itsName").getValue(String.class);
                    thtrs.add(nmBioskop);
                }
                spinnerThtr = findViewById(R.id.spnrBioskop);
                ArrayAdapter<String> thtrAdapter = new ArrayAdapter<String>(InputDetActivity.this, android.R.layout.simple_spinner_item, thtrs);
                thtrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerThtr.setAdapter(thtrAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        spinnerTime = findViewById(R.id.spnrTime);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapter);

        spinnerSeat = findViewById(R.id.spnrSeat);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this,
                R.array.seat_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSeat.setAdapter(adapters);

        sbmtBtn = findViewById(R.id.sbmtButton);
        sbmtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra("movie_idx", getIntent().getExtras().get("movie_idx").toString());
                intent.putExtra("time", spinnerTime.getSelectedItem().toString());
                intent.putExtra("theatre", spinnerThtr.getSelectedItem().toString());
                intent.putExtra("seat", spinnerSeat.getSelectedItem().toString());
                startActivity(intent);
            }
        });
    }
}