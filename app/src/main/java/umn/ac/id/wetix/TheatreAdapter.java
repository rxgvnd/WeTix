package umn.ac.id.wetix;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import static java.lang.Math.round;

public class TheatreAdapter extends FirebaseRecyclerAdapter<TheatreHelper, TheatreAdapter.theatresViewholder> {
    public double lat1, lng1, rad = 6372800;
    public TheatreAdapter(
            @NonNull FirebaseRecyclerOptions<TheatreHelper> options, double currLat, double currLong
    ){
        super (options);
        this.lat1 = currLat;
        this.lng1 = currLong;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull TheatreAdapter.theatresViewholder holder, int position, @NonNull @NotNull TheatreHelper model) {
        holder.theatreName.setText(model.getItsName());

        //caclulate distance
        double lat2 = model.getLatitude();;
        double lng2 = model.getLongitude();
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double tempDist =  Math.round(rad*c);
        if(tempDist >= 1000) {
            int distKM = (int) tempDist/1000;
            String dist = Integer.toString(distKM);
            holder.jarak.setText(dist + " Kilometer");
        }else {
            String dist = Double.toString(tempDist);
            holder.jarak.setText(dist + " Meter");
        }
    }

    @NonNull
    @NotNull
    @Override
    public TheatreAdapter.theatresViewholder
    onCreateViewHolder(@NonNull @NotNull ViewGroup parent,
                       int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.theatre, parent, false);
        return new TheatreAdapter.theatresViewholder(view);
    }
    class theatresViewholder extends RecyclerView.ViewHolder{
        TextView theatreName, jarak;
        public theatresViewholder(@NonNull View itemView){
            super(itemView);
            theatreName = itemView.findViewById(R.id.namaBioskop);
            jarak = itemView.findViewById(R.id.jarak);
        }
    }
}
