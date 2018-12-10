package com.dmytro.lisovyi.earthquakemap.ui;

import com.dmytro.lisovyi.earthquakemap.api.Repository;
import com.dmytro.lisovyi.earthquakemap.models.Earthquake;
import com.dmytro.lisovyi.earthquakemap.utils.DateTimeUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EarthquakesViewModel extends ViewModel {

    private LiveData<List<Earthquake>> earthquakeData;

    private Repository repository;

    public EarthquakesViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Earthquake>> getLastEarthquakes() {
        if (earthquakeData == null) {
            earthquakeData = repository.getEarthquakes(
                    DateTimeUtils.getBeginOfCurrentDay(),
                    DateTimeUtils.getBeginOfNextDay());
        }
        return earthquakeData;
    }

    public void refreshEarthquakes() {
        repository.refreshEarthquakes(
                    DateTimeUtils.getBeginOfCurrentDay(),
                    DateTimeUtils.getBeginOfNextDay());
    }

    static class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        private Repository repository;

        public ViewModelFactory(Repository repository) {
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new EarthquakesViewModel(repository);
        }
    }
}
