package co.edu.udea.gr06_20171compumovil.lab4.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import co.edu.udea.gr06_20171compumovil.lab4.R;
import co.edu.udea.gr06_20171compumovil.lab4.pojos.Event;

public class EventDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EVENT = "ARG_EVENT";

    // TODO: Rename and change types of parameters
    public Event mEvent;
    public TextView mNameView;
    public TextView mDescriptionView;
    public ImageView mEventImageView;
    public RatingBar mRatingBar;

    private OnFragmentInteractionListener mListener;

    public EventDetailFragment(Event event) {
        // Required empty public constructor
        mEvent = event;
    }

//    // TODO: Rename and change types and number of parameters
//    public static EventDetailFragment newInstance(Event event) {
//        EventDetailFragment fragment = new EventDetailFragment();
//        fragment.mEvent = event;
////        Bundle args = new Bundle();
////        args.putString(ARG_EVENT, event);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // -- inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.fragment_event_detail, container,false);

        Log.d("FROM_DETAIL", mEvent.getDescription());
        mNameView = (TextView) myInflatedView.findViewById(R.id.event_detail_name);
        mDescriptionView = (TextView) myInflatedView.findViewById(R.id.event_detail_description);
        mEventImageView = (ImageView) myInflatedView.findViewById(R.id.event_detail_picture);
        mRatingBar = (RatingBar) myInflatedView.findViewById(R.id.event_detail_rating);
//
//
//
        mNameView.setText(mEvent.getName().toString());
        mDescriptionView.setText(mEvent.getDescription().toString());
        Picasso.with(myInflatedView.getContext()).load(mEvent.getPicture()).into(mEventImageView);
        mRatingBar.setRating(mEvent.getScore());
        // Inflate the layout for this frag
        return myInflatedView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
