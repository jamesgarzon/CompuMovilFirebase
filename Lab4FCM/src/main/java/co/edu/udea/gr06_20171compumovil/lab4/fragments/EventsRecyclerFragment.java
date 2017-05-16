package co.edu.udea.gr06_20171compumovil.lab4.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.gr06_20171compumovil.lab4.EventsAdapter;
import co.edu.udea.gr06_20171compumovil.lab4.R;
import co.edu.udea.gr06_20171compumovil.lab4.pojos.Event;

/**
 * Created by admin on 29/04/2017.
 */

public class EventsRecyclerFragment extends Fragment {
    private RecyclerView recyclerView;
    private EventsAdapter eAdapter;
    private List<Event> evList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        evList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //getEvents();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Lista de eventos");
    }
}
