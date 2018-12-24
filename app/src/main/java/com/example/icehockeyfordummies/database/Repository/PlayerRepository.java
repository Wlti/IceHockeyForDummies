package com.example.icehockeyfordummies.database.Repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import com.example.icehockeyfordummies.database.Entity.PlayerEntity;
import com.example.icehockeyfordummies.database.firebase.PlayerLiveData;
import com.example.icehockeyfordummies.database.firebase.PlayersLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PlayerRepository {
    private static final String TAG = "PlayerRepository";
    private static PlayerRepository sInstance;
    public static PlayerRepository getInstance() {
        if (sInstance == null) {
            synchronized (PlayerRepository.class) {
                if (sInstance == null) {
                    sInstance = new PlayerRepository();
                }
            }
        }
        return sInstance;
    }



    public LiveData<PlayerEntity> getPlayerById (final String playerId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("players")
                .child(playerId);
        return new PlayerLiveData(reference);
    }



    public LiveData<List<PlayerEntity>> getAllPlayers () {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("players");
        return new PlayersLiveData(reference);
    }



    public void insert(final PlayerEntity player) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("players");
        reference.child(player.getIdPlayer()).child("number").setValue(player.getNumberPlayer());
        reference.child(player.getIdPlayer()).child("firstname").setValue(player.getFirstNamePlayer());
        reference.child(player.getIdPlayer()).child("lastname").setValue(player.getLastNamePlayer());
        reference.child(player.getIdPlayer()).child("birthdate").setValue(player.getBirthdatePlayer());
        reference.child(player.getIdPlayer()).child("picture").setValue(player.getPicturePlayer());
        reference.child(player.getIdPlayer()).child("position").setValue(player.getPositionPlayer());
        reference.child(player.getIdPlayer()).child("license").setValue(player.getLicensePlayer());
        reference.child(player.getIdPlayer()).child("points").setValue(player.getPointsPlayer());
        reference.child(player.getIdPlayer()).child("FK_idClub").setValue(player.getFK_idClub());
    }


    public void update(final PlayerEntity player) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("players");
        reference.child(player.getIdPlayer()).child("number").setValue(player.getNumberPlayer());
        reference.child(player.getIdPlayer()).child("firstname").setValue(player.getFirstNamePlayer());
        reference.child(player.getIdPlayer()).child("lastname").setValue(player.getLastNamePlayer());
        reference.child(player.getIdPlayer()).child("birthdate").setValue(player.getBirthdatePlayer());
        reference.child(player.getIdPlayer()).child("picture").setValue(player.getPicturePlayer());
        reference.child(player.getIdPlayer()).child("position").setValue(player.getPositionPlayer());
        reference.child(player.getIdPlayer()).child("license").setValue(player.getLicensePlayer());
        reference.child(player.getIdPlayer()).child("points").setValue(player.getPointsPlayer());
        reference.child(player.getIdPlayer()).child("FK_idClub").setValue(player.getFK_idClub());
    }


    public void delete(final PlayerEntity player) {
        FirebaseDatabase.getInstance().getReference("players").child(player.getIdPlayer()).removeValue((databaseError, databaseReference) -> {
            if (databaseError != null) {
                Log.d(TAG, "Delete failure!", databaseError.toException());
            } else {
                Log.d(TAG, "Delete successful!");
            }
        });
    }


}

