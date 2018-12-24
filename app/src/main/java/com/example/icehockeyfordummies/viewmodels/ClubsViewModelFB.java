package com.example.icehockeyfordummies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.icehockeyfordummies.BaseApp;
import com.example.icehockeyfordummies.database.Entity.ClubEntity;
import com.example.icehockeyfordummies.database.Repository.ClubRepository;

import java.util.List;


// Try to create a ViewModel for all the clubs, but it didn't work properly
public class ClubsViewModelFB extends AndroidViewModel {

    private static final String TAG = "ClubsList";
    private ClubRepository repository;

    private final MediatorLiveData<List<ClubEntity>> observable;


    public ClubsViewModelFB(@NonNull Application application, final String clubId,
                          ClubRepository repository) {
        super(application);

        this.repository = repository;

        observable = new MediatorLiveData<>();
        observable.setValue(null);

        LiveData<List<ClubEntity>> clubs = repository.getAllClubs();
        observable.addSource(clubs, observable::setValue);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final ClubRepository repository;
        private final String clubId;

        public Factory(@NonNull Application application, String clubId) {
            this.application = application;
            this.clubId = clubId;
            repository = ((BaseApp) application).getClubRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ClubsViewModelFB(application,clubId, repository);
        }
    }

    public LiveData<List<ClubEntity>> getAllClubs() {
        return observable;
    }

    public void deleteClub(ClubEntity club) {
        ((BaseApp) getApplication()).getClubRepository()
                .delete(club);
    }
    public void updateAccount(ClubEntity club) {
        ((BaseApp) getApplication()).getClubRepository()
                .update(club);
    }

}
