package com.dmytro.lisovyi.earthquakemap.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmytro.lisovyi.earthquakemap.ComponentProvider;
import com.dmytro.lisovyi.earthquakemap.R;
import com.dmytro.lisovyi.earthquakemap.models.Earthquake;
import com.dmytro.lisovyi.earthquakemap.ui.EarthquakesViewModel.ViewModelFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MapFragment extends Fragment {

    private EarthquakesViewModel earthquakesViewModel;

    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onStart();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
            }
        });

        earthquakesViewModel = ViewModelProviders.of(this,
                new ViewModelFactory(getComponentProvider().getRepository()))
                .get(EarthquakesViewModel.class);

        earthquakesViewModel.getLastEarthquakes().observe(this, new Observer<List<Earthquake>>() {
            @Override
            public void onChanged(List<Earthquake> earthquakes) {
                if (googleMap != null) {
                    googleMap.clear();
                    for (Earthquake earthquake : earthquakes) {
                        addEarthquakeToMap(earthquake);
                    }
                }
            }
        });
    }

    private ComponentProvider getComponentProvider() {
        return (ComponentProvider) getActivity().getApplication();
    }

    private void addEarthquakeToMap(Earthquake earthquake) {
        LatLng position = new LatLng(earthquake.getLatitude(), earthquake.getLongitude());
        googleMap.addMarker(new MarkerOptions()
                .position(position)
                .title(String.valueOf(earthquake.getMagnitude()))
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
