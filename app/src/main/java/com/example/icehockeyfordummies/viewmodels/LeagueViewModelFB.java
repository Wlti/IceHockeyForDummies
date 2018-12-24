package com.example.icehockeyfordummies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.icehockeyfordummies.BaseApp;
import com.example.icehockeyfordummies.database.Repository.LeagueRepository;
import com.example.icehockeyfordummies.database.Entity.LeagueEntity;


public class LeagueViewModelFB extends AndroidViewModel {
    private static final String TAG = "LeagueViewModel";
    private LeagueRepository repository;


    // MediatorLiveData to observe other LiveData objects
    private final MediatorLiveData<LeagueEntity> observableLeague;

    public LeagueViewModelFB(@NonNull Application application,
                           final String idLeague, LeagueRepository repository) {
        super(application);
        this.repository = repository;

        // Set the observableClient by default
        observableLeague = new MediatorLiveData<>();
        observableLeague.setValue(null);

        // Observe the changes and forward them
        LiveData<LeagueEntity> league = repository.getLeagueById(idLeague);
        observableLeague.addSource(league, observableLeague::setValue);
    }


    // Inject the idLeague into the ViewModel
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String idLeague;
        private final LeagueRepository repository;

        public Factory(@NonNull Application application, String idLeague) {
            this.application = application;
            this.idLeague = idLeague;
            repository = ((BaseApp) application).getLeagueRepository();
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new LeagueViewModelFB(application, idLeague, repository);
        }
    }


    // Make the LiveData LeagueEntity observable
    public LiveData<LeagueEntity> getLeague() {
        return observableLeague;
    }

    public void updateLeague(LeagueEntity league) {
        ((BaseApp) getApplication()).getLeagueRepository()
                .update(league);
    }
    public void deleteLeague(LeagueEntity league) {
        ((BaseApp) getApplication()).getLeagueRepository()
                .delete(league);
    }
    }

