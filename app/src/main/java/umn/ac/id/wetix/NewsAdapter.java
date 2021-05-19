package umn.ac.id.wetix;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
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

import java.util.Objects;

public class NewsAdapter extends FirebaseRecyclerAdapter<NewsHelper, NewsAdapter.newsViewholder> {
    Context context;
    public NewsAdapter(
            @NonNull FirebaseRecyclerOptions<NewsHelper> options1,
            Context context
    ){
        super (options1);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NewsAdapter.newsViewholder holder,
                                    int position, @NonNull NewsHelper model) {
        Uri uri = Uri.parse(model.getPict());
        Picasso.get().load(uri).into(holder.pict);
        holder.headline.setText(model.getHeadline());
        holder.rtLy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String goToNews = getRef(position).getKey();
                Intent intent = new Intent(context, NewsPageActivity.class);
                intent.putExtra("news_idx", goToNews);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public newsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news, parent, false);
        return new NewsAdapter.newsViewholder(view);
    }

    public class newsViewholder
            extends RecyclerView.ViewHolder {
        ImageView pict;
        TextView headline;
        RelativeLayout rtLy;
        public newsViewholder(@NonNull View itemView)
        {
            super(itemView);
            pict = itemView.findViewById(R.id.pictnya);
            headline = itemView.findViewById(R.id.headlinenya);
            rtLy = itemView.findViewById(R.id.rLay);
        }
    }
}
