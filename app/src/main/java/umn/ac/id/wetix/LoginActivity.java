package umn.ac.id.wetix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.text.Html.fromHtml;

public class LoginActivity extends AppCompatActivity {

    EditText TxEmail, TxPassword;
    Button BtnLogin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TxEmail = findViewById(R.id.txUsername);
        TxPassword = findViewById(R.id.txPassword);
        BtnLogin = findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        if(fAuth.getCurrentUser() != null){
            String toastMessage = "Already Logged In, Redirecting . . .";
            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Dashboard.class));
            finish();
        }

        TextView tvCreateAccount = findViewById(R.id.tvCreateAccount);
        tvCreateAccount.setText(fromHtml("I don't have account yet. " + "</font><font color='#3b5998'>create one</font>"));
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = TxEmail.getText().toString().trim();
                String password = TxPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    TxEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    TxEmail.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    TxPassword.setError("Password Must be >= 6 Characters");
                    return;
                }


                // authenticate the user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, Dashboard.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "Email or Password is Wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }
}