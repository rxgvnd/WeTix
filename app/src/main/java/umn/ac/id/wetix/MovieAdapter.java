package umn.ac.id.wetix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class MovieAdapter extends FirebaseRecyclerAdapter <MovieHelper, MovieAdapter.moviesViewholder> {
    Context context;
    public MovieAdapter(
            @NonNull FirebaseRecyclerOptions<MovieHelper> options,
            Context context
    ){
        super (options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull MovieAdapter.moviesViewholder holder,
                                    int position, @NonNull @NotNull MovieHelper model) {
        Uri uri = Uri.parse(model.getPoster());
        Picasso.get().load(uri).into(holder.poster);
        holder.title.setText(model.getNamemovie());
        holder.rtLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goToMovie = getRef(position).getKey();
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("movie_idx", goToMovie);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public moviesViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie, parent, false);
        return new MovieAdapter.moviesViewholder(view);
    }

    class moviesViewholder
            extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title;
        RelativeLayout rtLy;
        public moviesViewholder(@NonNull View itemView)
        {
            super(itemView);
            poster = itemView.findViewById(R.id.posternya);
            title = itemView.findViewById(R.id.judulnya);
            rtLy = itemView.findViewById(R.id.rLay);
        }
    }
}
