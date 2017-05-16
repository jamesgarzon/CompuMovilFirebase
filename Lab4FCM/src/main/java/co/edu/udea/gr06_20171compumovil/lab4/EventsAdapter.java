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

import java.util.List;

import co.edu.udea.gr06_20171compumovil.lab4.pojos.Event;

/**
 * Created by admin on 28/04/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final List<Event> events;
    private Context contexto;
    private static ClickListener clickListener;
    private Event event;

    public EventsAdapter(Context context, List<Event> events) {
        this.contexto = context;
        this.events = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*holder.mItem = events.get(position);
        holder.mNameView.setText(events.get(position).getName());
        holder.mDescriptionView.setText(events.get(position).getDescription());*/
        //Picasso.with(holder.mEventImageView.getContext()).load(events.get(position).getPicture()).resize(170, 170).into(holder.mEventImageView);


        event = events.get(position);
        Log.i(String.valueOf(position), "onBindViewHolder: ");

        Log.d("Entre a login process", "loginProcess: ");

        Log.d(event.getName(), "onBindViewHolder: ");
        holder.mNameView.setText(event.getName());

        Log.d(event.getDescription(), "onBindViewHolder: ");
        holder.mDescriptionView.setText(event.getDescription());

        Log.d(String.valueOf(event.getScore()), "onBindViewHolder: ");
        holder.mRatingView.setRating(Float.parseFloat(event.getScore()));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Log.i("ENTROO ", "entro a setitemclick en EventsAdapter1");
        EventsAdapter.clickListener = clickListener;
    }

    private void mostraDetalle(Event ev, View view) {

       /* Log.i("ENTROO Descripcion ", "ENTROOOOOOOO ");
        // Log.i("ENTROO Descripcion ",aux.getString(items.getColumnIndex("descripcion")) );

        Intent intent = new Intent(view.getContext(), EventsDetail.class);
        Bundle dato = new Bundle();
        dato.putInt("id",ev.id);
        Log.d(String.valueOf(ev.id), "iddddd: ");

        dato.putString("nombre", ev.nombre );
        dato.putString("descripcion", ev.descripcion);
        dato.putFloat("puntuacion", ev.puntuacion);
        dato.putString("responsable", ev.responsable);
        dato.putString("fecha", ev.fecha);
        dato.putString("ubicacion", ev.ubicacion);
        dato.putString("infoGeneral",ev.infoGeneral);
        //dato.putString("foto", aux.getString(aux.getColumnIndex("foto")));
        intent.putExtras(dato);
        contexto.startActivity(intent);*/

    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final View mView;
        public final TextView mNameView;
        public final TextView mDescriptionView;
        public final ImageView mEventImageView;
        public Event mItem;
        public final RatingBar mRatingView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mDescriptionView = (TextView) view.findViewById(R.id.description_view);
            mEventImageView = (ImageView) view.findViewById(R.id.event_picture);
            mRatingView = (RatingBar) itemView.findViewById(R.id.event_rating);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mostraDetalle(event, v);
//            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }
}
