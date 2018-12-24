package com.example.icehockeyfordummies.database.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.icehockeyfordummies.database.Entity.PlayerEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class PlayerLiveData extends LiveData<PlayerEntity> {

    private static final String TAG = "PlayerLiveData";
    private final DatabaseReference mReference;
    private final String mOwner;
    private final PlayerLiveData.MyValueEventListener mListener = new PlayerLiveData.MyValueEventListener();

    public PlayerLiveData(DatabaseReference ref) {
        mReference = ref;
        mOwner = ref.getParent().getParent().getKey();
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
            PlayerEntity entity = dataSnapshot.getValue(PlayerEntity.class);
            entity.setIdPlayer(dataSnapshot.getKey());
            setValue(entity);
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + mReference, databaseError.toException());
        }
    }
}