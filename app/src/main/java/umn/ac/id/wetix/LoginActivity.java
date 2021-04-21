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

    EditText TxUsername, TxPassword;
    Button BtnLogin;
//    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TxUsername = findViewById(R.id.txUsername);
        TxPassword = findViewById(R.id.txPassword);
        BtnLogin = findViewById(R.id.btnLogin);
//        dbHelper = new DBHelper(this);
        SharedPrefManager sharedPrefManager;
        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.getSPSudahLogin()){
            String toastMessage = "Already Logged In, Redirecting . . .";
            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
            Intent hvLoggedIN = new Intent(LoginActivity.this, Dashboard.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            hvLoggedIN.putExtra("FROM_ACTIVITY", "login");
            startActivity(hvLoggedIN);
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
//        BtnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = TxUsername.getText().toString().trim();
//                String password = TxPassword.getText().toString().trim();
//
////                Boolean res = dbHelper.checkUser(username, password);
//                if (res == true){
//                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, username);
//                    // Shared Pref ini berfungsi untuk menjadi trigger session login
//                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
//                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(LoginActivity.this, Dashboard.class));
//                } else {
//                    Toast.makeText( LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
    public static Spanned fromHtml(String html) {
            Spanned result;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
            }else {
                result = Html.fromHtml(html);
            }
            return result;
    }
}