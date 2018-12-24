package com.example.icehockeyfordummies.database.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.icehockeyfordummies.database.Entity.ClubEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ClubsLiveData extends LiveData<List<ClubEntity>> {

    private static final String TAG = "ClubsLiveData";
    private final DatabaseReference mReference;
    private final MyValueEventListener mListener = new MyValueEventListener();


    public ClubsLiveData(DatabaseReference ref) {
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
            setValue(toClubs(dataSnapshot));
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + mReference, databaseError.toException());
        }
    }



    private List<ClubEntity> toClubs(DataSnapshot snapshot) {
        List<ClubEntity> clubs = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ClubEntity entity = childSnapshot.getValue(ClubEntity.class);
            entity.setIdClub(childSnapshot.getKey());
            clubs.add(entity);
        }
        return clubs;
    }
}

