package umn.ac.id.wetix;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTheatre {
    Context context;
    public void showPopupWindow(final View view, Context context) {
        this.context = context;
        EditText etName, etLatitude, etLongitude, etHarga;
        ImageButton map;
        FirebaseDatabase root;
        DatabaseReference reference;
        root = FirebaseDatabase.getInstance();
        reference = root.getReference("theatres");

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_theatre, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler
        etName = popupView.findViewById(R.id.txTheatreName);
        etLatitude = popupView.findViewById(R.id.txLatitude);
        etLongitude = popupView.findViewById(R.id.txLongitude);
        etHarga = popupView.findViewById(R.id.txHarga);
        map = popupView.findViewById(R.id.mapz);
        Button sendTheatre = popupView.findViewById(R.id.sendTheatre);


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, MapsActivity.class);
                intent.putExtra("fromAct", "add");
                context.startActivity(intent);
            }
        });

        sendTheatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(etName.getText().toString().trim())){
                    etName.setError("Name is Required.");
                    return;
                }

                if(TextUtils.isEmpty(etLongitude.getText().toString().trim())){
                    etLatitude.setError("Latitude is Required.");
                    return;
                }

                if(TextUtils.isEmpty(etLongitude.getText().toString().trim())){
                    etLongitude.setError("Longitude is Required.");
                    return;
                }
                if(TextUtils.isEmpty(etHarga.getText().toString().trim())){
                    etHarga.setError("Longitude is Required.");
                    return;
                }

                String name = etName.getText().toString().trim();
                Double longitude = Double.parseDouble(etLongitude.getText().toString().trim());
                Double latitude = Double.parseDouble(etLatitude.getText().toString().trim());
                long harga = Long.parseLong((etHarga.getText().toString().trim()));
                TheatreHelper theatre = new TheatreHelper();
                theatre.setItsName(name);
                theatre.setLatitude(latitude);
                theatre.setLongitude(longitude);
                theatre.setHarga(harga);
                        reference.child(name)
                                .setValue(theatre)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(v.getContext(), "Data Berhasil ditambah", Toast.LENGTH_SHORT).show();
                                        popupWindow.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("TAG", "onFailure: " + e.toString());
                                    }
                                });
            }
        });
    }
}
