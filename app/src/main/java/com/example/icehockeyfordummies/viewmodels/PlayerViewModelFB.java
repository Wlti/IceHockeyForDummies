package com.example.icehockeyfordummies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.icehockeyfordummies.BaseApp;
import com.example.icehockeyfordummies.database.Entity.LeagueEntity;
import com.example.icehockeyfordummies.database.Entity.PlayerEntity;
import com.example.icehockeyfordummies.database.Repository.PlayerRepository;


public class PlayerViewModelFB extends AndroidViewModel {
    private static final String TAG = "PlayerViewModel";
    private PlayerRepository repository;

    // MediatorLiveData to observe other LiveData objects
    private final MediatorLiveData<PlayerEntity> observablePlayer;

    public PlayerViewModelFB(@NonNull Application application,
                           final String idPlayer, PlayerRepository playerRepository) {
        super(application);
        repository = playerRepository;

        // observablePlayer set by default
        observablePlayer = new MediatorLiveData<>();
        observablePlayer.setValue(null);

        // Observe the changes and forward them
        LiveData<PlayerEntity> player = repository.getPlayerById(idPlayer);
        observablePlayer.addSource(player, observablePlayer::setValue);
    }


    // Inject the idPlayer into the ViewModel
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String idPlayer;
        private final PlayerRepository repository;


        public Factory(@NonNull Application application, String idPlayer) {
            this.application = application;
            this.idPlayer = idPlayer;
            repository = ((BaseApp) application).getPlayerRepository();
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new PlayerViewModelFB(application, idPlayer, repository);
        }
    }


    // Make the LiveData observable
    public LiveData<PlayerEntity> getPlayer() {
        return observablePlayer;
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
