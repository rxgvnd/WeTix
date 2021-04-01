package umn.ac.id.wetix;

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

import static android.text.Html.fromHtml;

public class LoginActivity extends AppCompatActivity {

    /*EditText TxUsername, TxPassword;
    Button BtnLogin;
    DBHelper dbHelper;*/

    private LinearLayout LoginAct;
    private Button pressLogin;
    EditText Username, Password;
    String Correct_Username = "uasmobile";
    String Correct_Pass = "uasmobilegenap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Username = findViewById(R.id.txUsername);
        Password = findViewById(R.id.txPassword);
        pressLogin = findViewById(R.id.btnLogin);

        pressLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        /*TxUsername = (EditText)findViewById(R.id.txUsername);
        TxPassword = (EditText)findViewById(R.id.txPassword);
        BtnLogin = (Button)findViewById(R.id.btnLogin);

        dbHelper = new DBHelper(this);

        TextView tvCreateAccount = (TextView)findViewById(R.id.tvCreateAccount);
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
                String username = TxUsername.getText().toString().trim();
                String password = TxPassword.getText().toString().trim();

                Boolean res = dbHelper.checkUser(username, password);
                if (res == true){
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, Dashboard.class));
                } else {
                    Toast.makeText( LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static Spanned fromHtml(String html) {
            Spanned result;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
            }else {
                result = Html.fromHtml(html);
            }
            return result;*/
    }
}