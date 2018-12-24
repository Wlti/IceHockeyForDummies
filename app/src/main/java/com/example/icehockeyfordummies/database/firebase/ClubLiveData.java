package com.example.icehockeyfordummies.database.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.icehockeyfordummies.database.Entity.ClubEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class ClubLiveData extends LiveData<ClubEntity> {

        private static final String TAG = "ClubLiveData";
        private final DatabaseReference mReference;
        private final MyValueEventListener mListener = new MyValueEventListener();


        public ClubLiveData(DatabaseReference ref) {
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
                ClubEntity entity = dataSnapshot.getValue(ClubEntity.class);
                entity.setIdClub(dataSnapshot.getKey());
                setValue(entity);
            }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + mReference, databaseError.toException());
        }
    }
}

