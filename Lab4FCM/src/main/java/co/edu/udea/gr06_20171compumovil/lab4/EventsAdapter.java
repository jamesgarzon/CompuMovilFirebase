package co.edu.udea.gr06_20171compumovil.lab4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.edu.udea.gr06_20171compumovil.lab4.fragments.EventsFragment;
import co.edu.udea.gr06_20171compumovil.lab4.pojos.Event;

/**
 * Created by admin on 28/04/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final List<Event> mValues;
    private final EventsFragment.OnListFragmentInteractionListener mListener;

    public EventsAdapter(List<Event> items, EventsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        Log.d("VIEWHOLDER", "viewHolder: "+ mValues.toString());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mDescriptionView.setText(mValues.get(position).getDescription());
        if (!mValues.get(position).getPicture().isEmpty()){
            Picasso.with(holder.mEventImageView.getContext()).load(mValues.get(position).getPicture()).resize(170, 170).into(holder.mEventImageView);
        }
        holder.mRatingBar.setRating(mValues.get(position).getScore());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mDescriptionView;
        public final ImageView mEventImageView;
        public final RatingBar mRatingBar;
        public Event mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mDescriptionView = (TextView) view.findViewById(R.id.description_view);
            mEventImageView = (ImageView) view.findViewById(R.id.event_picture);
            mRatingBar = (RatingBar) view.findViewById(R.id.event_rating);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDescriptionView.getText() + "'";
        }
    }
}
