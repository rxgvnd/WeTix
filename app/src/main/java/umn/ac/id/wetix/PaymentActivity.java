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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class PaymentActivity extends AppCompatActivity {
    private String retMovie, retTheatre, waktu, uid;
    ImageView poster;
    TextView judoel, desc, jmlKursi, totalPay;
    Button payBtn, cancelBtn;
    DatabaseReference refMovie = FirebaseDatabase.getInstance().getReference("listmovie");
    DatabaseReference refTheat = FirebaseDatabase.getInstance().getReference("theatres");
    DatabaseReference refSaldo = FirebaseDatabase.getInstance().getReference("UsersBalance");
    DatabaseReference refTiket = FirebaseDatabase.getInstance().getReference("tiket");
    long tempHargaTheat, balanceNew, balanceNow, seat_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        retMovie = getIntent().getExtras().get("movie_idx").toString();
        retTheatre = getIntent().getExtras().get("theatre").toString();
        String jumlaKursi = getIntent().getExtras().get("seat").toString();
        waktu = getIntent().getExtras().get("time").toString();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        poster = findViewById(R.id.posterPay);
        judoel = findViewById(R.id.judoelPay);
        desc = findViewById(R.id.descPay);
        jmlKursi = findViewById(R.id.jmlKursi);
        jmlKursi.setText(jumlaKursi);
        totalPay = findViewById(R.id.totalPay);
        payBtn = findViewById(R.id.btnPay);
        Log.d("theatre", retTheatre);

        refMovie.child(retMovie).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                MovieHelper movie = snapshot.getValue(MovieHelper.class);
                Log.d("this", movie.getDesc());
                String title = movie.getNamemovie();
                String descr = movie.getDesc();
                Uri uri = Uri.parse(movie.getPoster());
                Picasso.get().load(uri).into(poster);
                judoel.setText(title);
                desc.setText(descr);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        refTheat.child(retTheatre).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                TheatreHelper theatre = snapshot.getValue(TheatreHelper.class);
                tempHargaTheat = theatre.getHarga();
                long totalharga = Long.parseLong(jumlaKursi) * tempHargaTheat;
                NumberFormat formatter = new DecimalFormat("#,###");
                String hargaTheat = formatter.format(totalharga);
                totalPay.setText(hargaTheat);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        refSaldo.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              balanceNow = (long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((balanceNew = balanceNow - tempHargaTheat) < 0){
                    balanceNew = balanceNow;
                    Toast.makeText(PaymentActivity.this, "Uang Anda Tidak Cukup, Silahkan TopUp Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    return;
                } else if((balanceNew - tempHargaTheat) >= 0) {
                    refSaldo.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(balanceNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                TicketHelper tiket = new TicketHelper(retMovie, tempHargaTheat, waktu, retTheatre, uid, Long.parseLong(jumlaKursi));
                                refTiket.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getSaltString()).setValue(tiket).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(PaymentActivity.this, "Payment Complete", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                            startActivity(intent);
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("error : ", "onFailure: " + e.toString());
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: " + e.toString());
                        }
                    });
                }
            }
        });
        cancelBtn = findViewById(R.id.btnCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}