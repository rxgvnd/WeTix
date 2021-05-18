package umn.ac.id.wetix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class OrderActivity extends AppCompatActivity {
    ImageView poster;
    TextView judoel, durasi, descr, year;
    Button ordr;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("listmovie");
    private String retMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        poster = findViewById(R.id.poster);
        judoel = findViewById(R.id.judoel);
        durasi = findViewById(R.id.durasi);
        descr = findViewById(R.id.descr);
        year = findViewById(R.id.year);
        ordr = findViewById(R.id.btnOrdr);
        retMovie = getIntent().getExtras().get("movie_idx").toString();
        Log.d("this", retMovie);

        ref.child(retMovie).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                MovieHelper movie = snapshot.getValue(MovieHelper.class);
                Log.d("this", movie.getDesc());
                String title = movie.getNamemovie();
                String desc = movie.getDesc();
                String dur = movie.getDuration();
                long yr = movie.getYear();
                String yrr = String.valueOf(yr);
                Uri uri = Uri.parse(movie.getPoster());
                Picasso.get().load(uri).into(poster);
                judoel.setText(title);
                durasi.setText(dur);
                year.setText(yrr);
                descr.setText(desc);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        ordr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, InputDetActivity.class);
                intent.putExtra("movie_idx", retMovie);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }
}