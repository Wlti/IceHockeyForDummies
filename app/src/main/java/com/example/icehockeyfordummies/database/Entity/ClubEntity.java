package com.example.icehockeyfordummies.database.Entity;

import android.support.annotation.NonNull;

import com.example.icehockeyfordummies.models.Club;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

public class ClubEntity implements Club {

    private String idClub;
    private String nameClub;
    private String logoClub;
    private String FK_idLeague;

    // Empty constructor
    public ClubEntity() {}


    // Constructor using parameters
    public ClubEntity(@NonNull String nameClub, String logoClub, String idLeague) {
        this.nameClub = nameClub;
        this.logoClub = logoClub;
        this.FK_idLeague = idLeague;
    }


    public ClubEntity(Club club) {
        idClub = club.getIdClub();
        nameClub = club.getNameClub();
        logoClub = club.getLogoClub();
        FK_idLeague = club.getFK_idLeague();
    }


    @Exclude
    @Override
    public String getIdClub() {
        return idClub;
    }

    public void setIdClub(String idClub) {
        this.idClub = idClub;
    }

    @Override
    public String getNameClub() {
        return nameClub;
    }

    public void setNameClub(String nameClub) {
        this.nameClub = nameClub;
    }

    @Override
    public String getLogoClub() {
        return logoClub;
    }

    public void setLogoClub(String logoClub) {
        this.logoClub = logoClub;
    }
    @Override
    public String getFK_idLeague() {
        return FK_idLeague;
    }

    public void setFK_idLeague(String FK_idleague) {
        this.FK_idLeague = FK_idleague;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ClubEntity)) return false;
        ClubEntity o = (ClubEntity) obj;
        return o.getIdClub()==(this.getIdClub());
    }

    @Override
    public String toString() {
        return nameClub;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nameClub", nameClub);
        result.put("logoClub", logoClub);
        result.put("FK_idLeague", FK_idLeague);
        return result;
    }
}
