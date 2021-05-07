package umn.ac.id.wetix;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class AddTheatre {
    Activity activity;


    public void showPopupWindow(final View view) {
        EditText etName, etLatitude, etLongitude;
        FirebaseAuth fAuth;
        FirebaseDatabase root;
        DatabaseReference reference;
        StorageReference upImageRef;
        fAuth =FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance();
        reference = root.getReference("theatres");
//        upImageRef = FirebaseStorage.getInstance().getReference("moviePosters");

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

       Button addPhoto =popupView.findViewById(R.id.btTheatrePict);
       addPhoto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               SelectImage();
           }
       });
//        Log.d("uri", filePath.toString());

        Button sendTheatre = popupView.findViewById(R.id.sendTheatre);
        sendTheatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                Double latitude = Double.parseDouble(etLatitude.getText().toString().trim());
                Double longitude = Double.parseDouble(etLongitude.getText().toString().trim());

                TheatreHelper theatre = new TheatreHelper();
                theatre.setItsName(name);
                theatre.setLatitude(latitude);
                theatre.setLongitude(longitude);
                        reference.child(name)
                                .setValue(theatre)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(v.getContext(), "Data Berhasil ditambah", Toast.LENGTH_SHORT).show();
//                                finish();
                                    }
                                });
                    }
                });
    }


}
