package com.dmytro.lisovyi.earthquakemap.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewSwitcher;

import com.dmytro.lisovyi.earthquakemap.ComponentProvider;
import com.dmytro.lisovyi.earthquakemap.R;
import com.dmytro.lisovyi.earthquakemap.models.Earthquake;
import com.dmytro.lisovyi.earthquakemap.ui.EarthquakesViewModel.ViewModelFactory;
import com.dmytro.lisovyi.earthquakemap.ui.adapters.EarthquakeAdapter;
import com.dmytro.lisovyi.earthquakemap.utils.NetworkUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListFragment extends Fragment {

    private EarthquakesViewModel viewModel;

    private EarthquakeAdapter adapter;
    private ViewSwitcher viewSwitcher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewSwitcher = view.findViewById(R.id.view_switcher);

        adapter = new EarthquakeAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(getComponentProvider().getRepository()))
                .get(EarthquakesViewModel.class);

        viewModel.getLastEarthquakes().observe(this, new Observer<List<Earthquake>>() {
            @Override
            public void onChanged(List<Earthquake> earthquakes) {
                if (earthquakes.size() == 0
                        && adapter.getItemCount() == 0
                        && !NetworkUtils.isOnline(getContext())) {
                    showPlaceholder(true);
                } else {
                    adapter.setEarthquakes(earthquakes);
                    showPlaceholder(false);
                }
            }
        });

        Button btnRefresh = view.findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.refreshEarthquakes();
            }
        });

    }

    private void showPlaceholder(boolean show) {
        if (show) {
            while (viewSwitcher.getCurrentView().getId() != R.id.ll_placeholder) {
                viewSwitcher.showNext();
            }
        } else {
            while (viewSwitcher.getCurrentView().getId() != R.id.recycler_view) {
                viewSwitcher.showNext();
            }
        }
    }

    private ComponentProvider getComponentProvider(){
        return (ComponentProvider) getActivity().getApplication();
    }

}
