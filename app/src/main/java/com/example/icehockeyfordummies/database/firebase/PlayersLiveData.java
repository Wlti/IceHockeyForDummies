package com.example.icehockeyfordummies.database.firebase;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.icehockeyfordummies.database.Entity.PlayerEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PlayersLiveData extends LiveData<List<PlayerEntity>> {

    private static final String TAG = "PlayersLiveData";
    private final DatabaseReference mReference;
    private final MyValueEventListener mListener = new MyValueEventListener();


    public PlayersLiveData(DatabaseReference ref) {
        mReference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        mReference.addValueEventListener(mListener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toPlayers(dataSnapshot));
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + mReference, databaseError.toException());
        }
    }



    private List<PlayerEntity> toPlayers(DataSnapshot snapshot) {
        List<PlayerEntity> players = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            PlayerEntity entity = childSnapshot.getValue(PlayerEntity.class);
            entity.setIdPlayer(childSnapshot.getKey());
            players.add(entity);
        }
        return players;
    }
}


