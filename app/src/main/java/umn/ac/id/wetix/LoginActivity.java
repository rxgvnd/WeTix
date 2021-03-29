package umn.ac.id.wetix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout LoginAct;
    private Button pressLogin;
    EditText Username, Password;
    String Correct_Username = "uasmobile";
    String Correct_Pass = "uasmobilegenap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        pressLogin = findViewById(R.id.pressLogin);

        pressLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(Username.getText().toString()) || TextUtils.isEmpty(Password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Empty Data!", Toast.LENGTH_LONG).show();
                }else if(Username.getText().toString().equals(Correct_Username)){
                    if (Password.getText().toString().equals(Correct_Pass)){
                        Toast.makeText(LoginActivity.this, "Selamat Datang!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}