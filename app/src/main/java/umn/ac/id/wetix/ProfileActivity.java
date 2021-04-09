package umn.ac.id.wetix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

public class ProfileActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText name, email, bday;
    TextView balance;
    ImageView profpic;
    User user;
    SharedPrefManager sharedPrefManager;
    Button saveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPrefManager = new SharedPrefManager(ProfileActivity.this);

        dbHelper = new DBHelper(this);
        user = dbHelper.getUser(sharedPrefManager.getID());
        profpic = findViewById(R.id.profpic);
        balance = findViewById(R.id.txBalance);
        balance.setText(String.format("Saldo : %s", user.getBalance()));
        name = findViewById(R.id.txName);
        name.setText(user.getName());
        email = findViewById(R.id.txEmail);
        email.setText(user.getEmail());
        bday = findViewById(R.id.txBirthday);
        bday.setText(user.getBday());
        //name = findViewById(R.id.txName);

        saveProfile = findViewById(R.id.saveProf);
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                String etName = name.getText().toString().trim();
                String etEmail = email.getText().toString().trim();
                String etBday = bday.getText().toString().trim();

                values.put(DBHelper.row_name, etName);
                values.put(DBHelper.row_email, etEmail);
                values.put(DBHelper.row_bday, etBday);
                if(dbHelper.updateData(values, sharedPrefManager.getID()) == true){
                    Toast.makeText(ProfileActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}