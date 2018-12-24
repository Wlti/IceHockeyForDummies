package com.example.icehockeyfordummies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.icehockeyfordummies.BaseApp;
import com.example.icehockeyfordummies.database.Repository.ClubRepository;
import com.example.icehockeyfordummies.database.Entity.ClubEntity;


public class ClubViewModelFB extends AndroidViewModel {
    private static final String TAG = "ClubViewModel";
    private ClubRepository repository;

    // MediatorLiveData to observe other LiveData objects
    private final MediatorLiveData<ClubEntity> observableClub;


    public ClubViewModelFB(@NonNull Application application,
                         final String idClub, ClubRepository clubRepository) {
        super(application);
        repository = clubRepository;

        // Set the observableClub by default
        observableClub = new MediatorLiveData<>();
        observableClub.setValue(null);

        // Observe the changes and forward them
        LiveData<ClubEntity> club = repository.getClubById(idClub);
        observableClub.addSource(club, observableClub::setValue);
    }


    // Inject the idClub into the ViewModel
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String idClub;
        private final ClubRepository repository;


        public Factory(@NonNull Application application, String idClub) {
            this.application = application;
            this.idClub = idClub;
            repository = ((BaseApp) application).getClubRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ClubViewModelFB(application, idClub, repository);
        }
    }


    // Make the LiveData ClubEntity observable
    public LiveData<ClubEntity> getClub() {
        return observableClub;
    }


    public void updateAccount(ClubEntity club) {
        ((BaseApp) getApplication()).getClubRepository()
                .update(club);
    }



    public void deleteAccount(ClubEntity club) {
        ((BaseApp) getApplication()).getClubRepository()
                .delete(club);
    }
    }
