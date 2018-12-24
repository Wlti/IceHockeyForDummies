package com.example.icehockeyfordummies.database.Repository;


import android.arch.lifecycle.LiveData;
import android.util.Log;
import com.example.icehockeyfordummies.database.Entity.LeagueEntity;
import com.example.icehockeyfordummies.database.firebase.LeagueLiveData;
import com.example.icehockeyfordummies.database.firebase.LeaguesLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LeagueRepository {

    private static final String TAG = "LeagueRepository";
    private static LeagueRepository sInstance;
    public static LeagueRepository getInstance() {
        if (sInstance == null) {
            synchronized (LeagueRepository.class) {
                if (sInstance == null) {
                    sInstance = new LeagueRepository();
                }
            }
        }
        return sInstance;
    }

    public LiveData<LeagueEntity> getLeagueById(final String leagueId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("leagues").child(leagueId);
        return new LeagueLiveData(reference);
    }


    public LiveData<List<LeagueEntity>> getAllLeagues() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("leagues");
        return new LeaguesLiveData(reference);
    }


    public void insert(final LeagueEntity league) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("leagues");
        reference.child(league.getIdLeague()).child("name").setValue(league.getNameLeague());
        reference.child(league.getIdLeague()).child("logo").setValue(league.getLogoLeague());
    }



    public void update(final LeagueEntity league) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("leagues");
        reference.child(league.getIdLeague()).child("name").setValue(league.getNameLeague());
        reference.child(league.getIdLeague()).child("logo").setValue(league.getLogoLeague());
    }


    public void delete(final LeagueEntity league) {
        FirebaseDatabase.getInstance().getReference("leagues").child(league.getIdLeague()).removeValue((databaseError, databaseReference) -> {
            if (databaseError != null) {
                Log.d(TAG, "Delete failure!", databaseError.toException());
            } else {
                Log.d(TAG, "Delete successful!");
            }
        });
    }
}
