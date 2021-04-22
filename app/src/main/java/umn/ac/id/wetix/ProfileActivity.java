package umn.ac.id.wetix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
//        balance.setText(String.format("Saldo : %s", user.getBalance()));
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
                if(dbHelper.updateData(values, sharedPrefManager.getID())){ //harusnya ada tru tapi kata android studio sama aja jadi ku apus
                    Toast.makeText(ProfileActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Toolbar toolbar = findViewById(R.id.profile_tool);
        setSupportActionBar(toolbar);
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
                startActivity(new Intent(ProfileActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}