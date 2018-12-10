package com.dmytro.lisovyi.earthquakemap.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmytro.lisovyi.earthquakemap.R;
import com.dmytro.lisovyi.earthquakemap.models.Earthquake;
import com.dmytro.lisovyi.earthquakemap.ui.adapters.EarthquakeAdapter.EarthquakeViewHolder;
import com.dmytro.lisovyi.earthquakemap.utils.DateTimeUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeViewHolder> {

    private List<Earthquake> earthquakes;

    public void setEarthquakes(List<Earthquake> earthquakes) {
        this.earthquakes = earthquakes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_earthquake, parent, false);
        return new EarthquakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {
        Earthquake earthquake = earthquakes.get(position);
        holder.time.setText(DateTimeUtils.getFormattedTime(earthquake.getTime()));
        holder.place.setText(earthquake.getPlace());
        holder.magnitude.setText(String.valueOf(earthquake.getMagnitude()));
    }

    @Override
    public int getItemCount() {
        return earthquakes == null ? 0 : earthquakes.size();
    }

    class EarthquakeViewHolder extends RecyclerView.ViewHolder {

        private TextView time;
        private TextView place;
        private TextView magnitude;

        EarthquakeViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_time);
            place = itemView.findViewById(R.id.tv_place);
            magnitude = itemView.findViewById(R.id.tv_magnitude);
        }
    }
}
