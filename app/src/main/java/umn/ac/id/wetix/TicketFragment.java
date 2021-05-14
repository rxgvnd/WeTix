package umn.ac.id.wetix;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TicketFragment extends Fragment {

    private RecyclerView ticketRV;
    TicketAdapter adapter;

    public TicketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        Query query = FirebaseDatabase.getInstance().getReference("tiket").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ticketRV = view.findViewById((R.id.recyclerTiketUser));
        ticketRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<TicketHelper> options = new FirebaseRecyclerOptions.Builder<TicketHelper>()
                .setQuery(query, TicketHelper.class)
                .build();
        adapter = new TicketAdapter(options, getContext());
        ticketRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false
        ));
        ticketRV.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}