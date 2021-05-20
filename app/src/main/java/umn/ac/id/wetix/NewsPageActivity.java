package umn.ac.id.wetix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class NewsPageActivity extends AppCompatActivity {
    ImageView PosterNews;
    TextView judul, IsiNews;
    Button btnBackHome;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("listnews");
    private String retNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        PosterNews = findViewById(R.id.PosterNews);
        judul = findViewById(R.id.judul);
        IsiNews = findViewById(R.id.IsiNews);
        retNews = getIntent().getExtras().get("news_idx").toString();
        Log.d("this", retNews);

        ref.child(retNews).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                NewsHelper news = snapshot.getValue(NewsHelper.class);
                Log.d("this", news.getContent());
                String title = news.getHeadline();
                String descnews = news.getContent();
                //long yr = movie.getYear();
                //String yrr = String.valueOf(yr);
                Uri uri = Uri.parse(news.getPict());
                Picasso.get().load(uri).into(PosterNews);
                judul.setText(title);
                IsiNews.setText(descnews);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        Toolbar toolbar = findViewById(R.id.news_tool);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}