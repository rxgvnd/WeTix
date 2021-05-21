package umn.ac.id.wetix;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class TicketAdapter extends FirebaseRecyclerAdapter<TicketHelper, TicketAdapter.ticketViewholder> {
    DatabaseReference refMovie = FirebaseDatabase.getInstance().getReference("listmovie");
    DatabaseReference refTheat = FirebaseDatabase.getInstance().getReference("theatres");
    DatabaseReference refTiket = FirebaseDatabase.getInstance().getReference("tiket");
    //DatabaseReference refTotal = FirebaseDatabase.getInstance().getReference("totalseat");
    FirebaseFirestore db;
    Context context;

    public TicketAdapter(
            @NonNull FirebaseRecyclerOptions<TicketHelper> options,
            Context context
    ){
        super (options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ticketViewholder holder, int position, @NonNull @NotNull TicketHelper model) {
        refMovie.child(model.getIdMovie()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                MovieHelper movie = snapshot.getValue(MovieHelper.class);
                String title = movie.getNamemovie();
                Uri uri = Uri.parse(movie.getPoster());
                Picasso.get().load(uri).into(holder.poster);
                holder.judul.setText(title);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        refTheat.child(model.getBioskop()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                TheatreHelper theatre = snapshot.getValue(TheatreHelper.class);
                holder.nama.setText("Bioskop " + theatre.getItsName());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        //refTotal.child(model.GetTotSeat()).addValueEventListener(new ValueEventListener() {
        //    @Override
        //    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
        //        TicketHelper Total = snapshot.getValue(TicketHelper.class);
        //        holder.TotSeat.setText(Total.GetTotSeat() + " Seat");
        //    }

        //    @Override
        //    public void onCancelled(@NonNull @NotNull DatabaseError error) {

        //    }
       // });
        holder.waktu.setText("Pukul " + model.getWaktu());

        //holder.cncl.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        showDialog(model, getRef(position).getKey());
        //    }
        //});

        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @NotNull
    @Override
    public TicketAdapter.ticketViewholder
    onCreateViewHolder(@NonNull @NotNull ViewGroup parent,
                       int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket, parent, false);
        return new TicketAdapter.ticketViewholder(view);
    }

    class ticketViewholder extends RecyclerView.ViewHolder{
        TextView judul, nama, waktu,TotSeat;
        ImageView poster;
        //Button cncl;
        public ticketViewholder(@NonNull View itemView){
            super(itemView);
            poster = itemView.findViewById(R.id.posterTiket);
            judul = itemView.findViewById(R.id.JudulFilm);
            nama = itemView.findViewById(R.id.namaBioskop);
            waktu = itemView.findViewById(R.id.waktu);
            //TotSeat = itemView.findViewById(R.id.TotSeat);
            //cncl = itemView.findViewById(R.id.cnclButton);
        }
    }

    private void showDialog(TicketHelper toDel, String id) throws Resources.NotFoundException {
        new AlertDialog.Builder(context)
                .setTitle("Confirmation")
                .setMessage(
                        "Do You Really Want to Cancel This Ticket?")
                .setPositiveButton(
                        context.getResources().getString(R.string.PostiveYesButton),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //Do Something Here
                                Log.d("test",id);
                                refTiket.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "Ticket Cancelled", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("error : ", "onFailure: " + e.toString());
                                            }
                                        });
                            }
                        })
                .setNegativeButton(
                        context.getResources().getString(R.string.NegativeNoButton),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                return;
                            }
                        }).show();
    }
}
