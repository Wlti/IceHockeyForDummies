package com.example.icehockeyfordummies.database.firebase;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.icehockeyfordummies.database.Entity.LeagueEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LeaguesLiveData extends LiveData<List<LeagueEntity>> {

    private static final String TAG = "LeagueLiveData";
    private final DatabaseReference mReference;
    private final MyValueEventListener mListener = new MyValueEventListener();


    public LeaguesLiveData(DatabaseReference ref) {
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
            setValue(toLeagues(dataSnapshot));
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + mReference, databaseError.toException());
        }
    }



    private List<LeagueEntity> toLeagues(DataSnapshot snapshot) {
        List<LeagueEntity> leagues = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            LeagueEntity entity = childSnapshot.getValue(LeagueEntity.class);
            entity.setIdLeague(childSnapshot.getKey());
            leagues.add(entity);
        }
        return leagues;
    }
}

