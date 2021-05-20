package umn.ac.id.wetix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.storage.StorageReference;

import static java.sql.Types.NULL;

public class TopUpActivity extends AppCompatActivity {

    ImageButton toProfile;
    ImageView profpic;
    TextView txEmail, txBalance, txBday, etName;
    EditText etAmt;
    Button topUp;
    FirebaseAuth fAuth;
    DatabaseReference refUser, refSaldo;
    String name, email;
    long updateBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        fAuth =fAuth.getInstance();
        refUser = FirebaseDatabase.getInstance().getReference("users");
        refSaldo = FirebaseDatabase.getInstance().getReference("UsersBalance");
        profpic = findViewById(R.id.profPict);
        txBalance = findViewById(R.id.saldo);
        etName = findViewById(R.id.name);
        txEmail = findViewById(R.id.email);

        refUser.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Mengambil daftar item dari database, setiap kali ada turunannya
                UserHelper user = dataSnapshot.getValue(UserHelper.class);
                email = user.getEmail();
                name = user.getName();
                txEmail.setText(email);
                etName.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        refSaldo.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Mengambil daftar item dari database, setiap kali ada turunannya
                updateBalance = (long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        topUp = findViewById(R.id.btnTopUp);
        etAmt = findViewById(R.id.masukkanUang);
        topUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uang = etAmt.getText().toString();
                updateBalance = updateBalance + Long.parseLong(uang);
                if(uang.isEmpty()){
                    etAmt.setError("Masukkin angka boss");
                    return;
                }
                refSaldo.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(updateBalance).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(TopUpActivity.this, "Top Up Complete", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (getApplicationContext(), Dashboard.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.toString());
                    }
                });
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbartopup);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toProfile = findViewById(R.id.toProfile);
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}