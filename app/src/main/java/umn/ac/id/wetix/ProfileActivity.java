package umn.ac.id.wetix;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.fetchProfile();
        profpic = findViewById(R.id.profpic);
        balance = findViewById(R.id.txBalance);
        //balance.setText(String.format("Saldo : %s", cursor.getString(7)));
        name = findViewById(R.id.txName);
        //name.setText(cursor.getString(3));
        email = findViewById(R.id.txEmail);
        email.setText(cursor.getString(1));
        bday = findViewById(R.id.txBirthday);
        //bday.setText(cursor.getString(6));
        //name = findViewById(R.id.txName);
    }

}