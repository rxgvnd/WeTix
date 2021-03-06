package umn.ac.id.wetix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvBday;
    private ImageButton btDatePicker;
    FirebaseAuth fAuth;
    DatabaseReference reference, refSaldo;
    EditText TxName, TxEmail, TxPassword, TxConPassword;
    Button BtnRegister;
    TextView mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TxName = findViewById(R.id.txUsernameReg);
        TxEmail = findViewById(R.id.txEmailReg);
        TxPassword = findViewById(R.id.txPasswordReg);
        TxConPassword = findViewById(R.id.txConPassword);
        BtnRegister = findViewById(R.id.btnRegister);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        tvBday = findViewById(R.id.tv_dateresult);
        btDatePicker = findViewById(R.id.bt_datepicker);
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        mLoginBtn = findViewById(R.id.regToLog);
        mLoginBtn.setText(fromHtml("Back to " +  "</font><font color='#3b5998'>Login</font>"));
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        refSaldo = FirebaseDatabase.getInstance().getReference("UsersBalance");
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            String toastMessage = "Already Logged In, Redirecting . . .";
            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Dashboard.class));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            finish();
        }

        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = TxName.getText().toString().trim();
                String email = TxEmail.getText().toString().trim();
                String password = TxPassword.getText().toString().trim();
                String conPassword = TxConPassword.getText().toString().trim();
                String bday = tvBday.getText().toString().trim();
                reference = FirebaseDatabase.getInstance().getReference("users");

                if(TextUtils.isEmpty(email)){
                    TxEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    TxPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    TxPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                if (!(TextUtils.equals(password,conPassword)) ){
                    TxPassword.setError("Password Didn't Match");
                    TxConPassword.setError("Password Didn't Match");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserHelper userModel = new UserHelper(name, bday, email);
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        refSaldo.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(RegisterActivity.this, Dashboard.class);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                        }else {
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

    }

    public static Spanned fromHtml(String html) {
        Spanned result;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvBday.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}

