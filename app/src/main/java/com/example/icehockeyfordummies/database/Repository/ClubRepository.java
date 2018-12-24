package com.example.icehockeyfordummies.database.Repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import com.example.icehockeyfordummies.database.Entity.ClubEntity;
import com.example.icehockeyfordummies.database.firebase.ClubLiveData;
import com.example.icehockeyfordummies.database.firebase.ClubsLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ClubRepository {
    private static final String TAG = "ClubRepository";
    private static ClubRepository sInstance;

    public static ClubRepository getInstance() {
        if (sInstance == null) {
            synchronized (ClubRepository.class) {
                if (sInstance == null) {
                    sInstance = new ClubRepository();
                }
            }
        }
        return sInstance;
    }

    public LiveData<ClubEntity> getClubById(final String clubId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clubs")
                .child(clubId);
        return new ClubLiveData(reference);
    }

    public LiveData<List<ClubEntity>> getAllClubs() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clubs");
        return new ClubsLiveData(reference);
    }

    public void insert(final ClubEntity club) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clubs");
        reference.child(club.getIdClub()).child("name").setValue(club.getNameClub());
        reference.child(club.getIdClub()).child("logo").setValue(club.getLogoClub());
        reference.child(club.getIdClub()).child("FKleague").setValue(club.getFK_idLeague());
    }


    public void update(final ClubEntity club) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("clubs");
        reference.child(club.getIdClub()).child("name").setValue(club.getNameClub());
        reference.child(club.getIdClub()).child("logo").setValue(club.getLogoClub());
        reference.child(club.getIdClub()).child("FKleague").setValue(club.getFK_idLeague());
    }


    public void delete(final ClubEntity club) {
        FirebaseDatabase.getInstance().getReference("clubs").child(club.getIdClub()).removeValue((databaseError, databaseReference) -> {
            if (databaseError != null) {
                Log.d(TAG, "Delete failure!", databaseError.toException());
            } else {
                Log.d(TAG, "Delete successful!");
            }
        });
    }
}