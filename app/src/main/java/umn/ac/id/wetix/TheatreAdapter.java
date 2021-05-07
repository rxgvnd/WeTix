package umn.ac.id.wetix;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class TheatreAdapter extends FirebaseRecyclerAdapter<TheatreHelper, TheatreAdapter.theatresViewholder> {
    public TheatreAdapter(
            @NonNull FirebaseRecyclerOptions<TheatreHelper> options
    ){
        super (options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull TheatreAdapter.theatresViewholder holder, int position, @NonNull @NotNull TheatreHelper model) {
        holder.theatreName.setText(model.getItsName());
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
        TextView theatreName;
        public theatresViewholder(@NonNull View itemView){
            super(itemView);

            theatreName = itemView.findViewById(R.id.namaBioskop);

        }

    }
}
