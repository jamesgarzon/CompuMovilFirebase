package co.edu.udea.gr06_20171compumovil.lab4.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.gr06_20171compumovil.lab4.EventsAdapter;
import co.edu.udea.gr06_20171compumovil.lab4.R;
import co.edu.udea.gr06_20171compumovil.lab4.pojos.Event;

/**
 * Created by admin on 29/04/2017.
 */

public class EventsFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    public String url = "https://stormy-oasis-88226.herokuapp.com/api";
    public List<Event> events = new ArrayList<>();
    public EventsAdapter mAdapter;
    public Button mUpdateEventsButton;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("events");

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventsFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EventsFragment newInstance(int columnCount) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

//        mUpdateEventsButton = (Button) container.findViewById(R.id.update_events);
//        mUpdateEventsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateEvents();
//            }
//        });
        mAdapter = new EventsAdapter(events, mListener);


        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(mAdapter);
        }

        updateEvents();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Event item);
    }

    public void updateEvents(){
// Write a message to the database


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                events.clear();
                Log.d("FIREBASEDDD", "ENTRO");
                for (DataSnapshot dataEvent : dataSnapshot.getChildren()) {
                    Event event = new Event();
                    event.setName(dataEvent.child("name").getValue().toString());
                    event.setDescription(dataEvent.child("description").getValue().toString());
                    event.setScore(Float.parseFloat(dataEvent.child("score").getValue().toString()));
                    event.setPicture(dataEvent.child("picture").getValue().toString());
                    Log.d("FIREBASEDDD", event.getName() + "   "+ event.getScore());
                    events.add(event);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });

//        //making object of RestAdapter
//        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(url).build();
//
//        //Creating Rest Services
//        RestInterface restInterface = adapter.create(RestInterface.class);
//
//        //Calling method to get whether report
//        restInterface.getEvents(new Callback<List<Event>>() {
//
//            @Override
//            public void success(List<Event> eventsResponse, Response response) {
//                Log.d("REST", "URL: "+ response.getUrl());
//                // Check if no view has focus:
//
//                String eventss = eventsResponse.toString();
//                Log.d("EVENTOS", "Events: "+ eventsResponse.toString());
//                events.clear();
//                events.addAll(eventsResponse);
//                mAdapter.notifyDataSetChanged();
//            }
//
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.d("REST", "Error: " + error.toString());
//            }
//        });
    }
}
