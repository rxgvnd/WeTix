package umn.ac.id.wetix;

import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;


public class ConfPass {
    EditText etConPassword;
    Button ok;
    UserHelper user;
    Uri pict;
    public void showPopupWindow(final View view, UserHelper upUser, Uri newPict, String userPass) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        this.user = upUser;
        this.pict = newPict;

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.conf_pass, null);

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


        etConPassword = popupView.findViewById(R.id.conPass);


        Button ok = popupView.findViewById(R.id.okay);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String conPassword = etConPassword.getText().toString().trim();
                if (!(TextUtils.equals(conPassword, userPass))) {
                    etConPassword.setError("Password Didn't Match");
                    return;
                }
                popupWindow.dismiss();
            }
        });
    }
}
