package umn.ac.id.wetix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private ImageButton btDatePicker;
    EditText etName, etPass;
    TextView txEmail, txBalance, txBday;
//    ImageView profpic;
    SharedPrefManager sharedPrefManager;
    Button saveProfile;
    FirebaseAuth fAuth;
    FirebaseDatabase root;
    DatabaseReference reference;
    String name, newName, email, password, newPassword, newBday, newPicture;
    int balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fAuth =fAuth.getInstance();
        root = FirebaseDatabase.getInstance();
        reference = root.getReference("users");
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//        profpic = findViewById(R.id.profpic);
        txBalance = findViewById(R.id.txBalance);
        etName = findViewById(R.id.txName);
        txEmail = findViewById(R.id.txEmail);
        etPass = findViewById(R.id.txChangePass);
        txBday = (TextView) findViewById(R.id.prof_tvdateresult);
        btDatePicker = (ImageButton) findViewById(R.id.prof_bt_datepicker);
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Mengambil daftar item dari database, setiap kali ada turunannya
                UserHelper user = dataSnapshot.getValue(UserHelper.class);
                email = user.getEmail();
                balance = user.getBalance();
                name = user.getName();
                newBday = user.getBday();
//                profpic = user.getPicture();
                password = user.getPassword();

                txEmail.setText(email);
                txBalance.setText("Rp. "+ balance);
                etName.setText(name);
                txBday.setText(newBday);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        newPassword = etPass.getText().toString().trim();

        saveProfile = findViewById(R.id.saveProf);
        if(TextUtils.isEmpty(newPassword)) {
            saveProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newName = etName.getText().toString();
//                  newPicture =
                    Log.e("ag", newName);
                    UserHelper upUser = new UserHelper();
                    upUser.setEmail(email);
                    upUser.setName(newName);
                    upUser.setBalance(balance);
                    upUser.setPicture("null");
                    upUser.setBday(newBday);
                    upUser.setPassword(password);
                    updateUser(upUser);
                }
            });
        }
        Toolbar toolbar = findViewById(R.id.profile_tool);
        setSupportActionBar(toolbar);
    }

    private void updateUser(UserHelper user){
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileActivity.this, "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.topup:
//                Intent toProfile = new Intent(ProfileActivity.this, ProfileActivity.class);
//                toProfile.putExtra("FROM_ACTIVITY", "songlist");
//                startActivity(toProfile);
                return true;
            case R.id.logout:
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txBday.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}