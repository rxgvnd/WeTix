package umn.ac.id.wetix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 22;
    private SimpleDateFormat dateFormatter;
    private ImageButton profpic;
    EditText etName, etPass;
    TextView txEmail, txBalance, txBday;
    Button saveProfile;
    DatabaseReference reference, refSaldo;
    StorageReference upImageRef;
    String name, newName, email, password, newPassword, newBday;
    long balance;
    private Uri filePath, newPict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ImageButton btDatePicker;
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        profpic = findViewById(R.id.profpic);
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

        profpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("users");
        upImageRef = FirebaseStorage.getInstance().getReference("usersProfPic");
        refSaldo = FirebaseDatabase.getInstance().getReference("UsersBalance");

        StorageReference profPicRef = upImageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        profPicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).resize(600,600).into(profpic);
            }
        });

        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Mengambil daftar item dari database, setiap kali ada turunannya
                UserHelper user = dataSnapshot.getValue(UserHelper.class);
                email = user.getEmail();
                name = user.getName();
                newBday = user.getBday();
                password = user.getPassword();

                txEmail.setText(email);
                etName.setText(name);
                txBday.setText(newBday);
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
                NumberFormat formatter = new DecimalFormat("#,###");
                balance = (long) dataSnapshot.getValue();
                String formattedNumber = formatter.format(balance);
                txBalance.setText("Rp." + formattedNumber);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        saveProfile = findViewById(R.id.saveProf);
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPassword = etPass.getText().toString().trim();
                if(TextUtils.isEmpty(newPassword)) {
                    newName = etName.getText().toString();
                    if(TextUtils.isEmpty(newName)){
                        etName.setError("Nama gabole kosong");
                        return;
                    }
                    UserHelper upUser = new UserHelper();
                    newPict = filePath;
                    upUser.setEmail(email);
                    upUser.setName(newName);
//                    upUser.setBalance(balance);
                    upUser.setBday(newBday);
                    upUser.setPassword(password);
                    updateUser(upUser, newPict);
                } else if(!(TextUtils.isEmpty(newPassword))) {
                    newName = etName.getText().toString();
                    newPassword = etPass.getText().toString().trim();
                    if(newPassword.length() < 6){
                        etPass.setError("Password Must be >= 6 Characters");
                        return;
                    }
                    UserHelper upUser = new UserHelper();
                    newPict = filePath;
                    upUser.setEmail(email);
                    upUser.setName(newName);
//                    upUser.setBalance(balance);
                    upUser.setBday(newBday);
                    upUser.setPassword(newPassword);
//                    ConfPass confPass = new ConfPass();
//                    confPass.showPopupWindow(view, upUser, newPict, password);
                    updateUser(upUser, newPict);
                }
            }
        });
    Toolbar toolbar = findViewById(R.id.profile_tool);
    setSupportActionBar(toolbar);
    }

    private void SelectImage()
    {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);
        int maxSize = 600;

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {
                Log.d("test", "test");
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();

                float bitmapRatio = (float) width / (float) height;
                if (bitmapRatio > 1) {
                    width = maxSize;
                    height = (int) (width / bitmapRatio);
                } else {
                    height = maxSize;
                    width = (int) (height * bitmapRatio);
                }
                bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                profpic.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    public void updateUser(UserHelper user, Uri imageUri){
        if(imageUri != null) {
            StorageReference fileRef = upImageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ProfileActivity.this, "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
//                                finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(getApplicationContext(),
                                    "Image Error" + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                }
            });
        } else if (user != null) {
            DatabaseReference userRef = reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            userRef.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ProfileActivity.this, "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan, gagal menyimpan data", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
                Intent toTopUp = new Intent(ProfileActivity.this, TopUpActivity.class);
                startActivity(toTopUp);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDateDialog(){
        DatePickerDialog datePickerDialog;
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