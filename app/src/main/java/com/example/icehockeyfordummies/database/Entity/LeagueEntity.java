package com.example.icehockeyfordummies.database.Entity;

import android.support.annotation.NonNull;
import com.example.icehockeyfordummies.models.League;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

public class LeagueEntity implements League {

    private String  idLeague;
    private String nameLeague;
    private String logoLeague;

    // Empty constructor
    public LeagueEntity() {}

    // Constructor using parameters
    public LeagueEntity(@NonNull String nameLeague, String logoLeague) {
        this.nameLeague = nameLeague;
        this.logoLeague = logoLeague;
    }

    // Constructor using methods
    public LeagueEntity(League league) {
        idLeague = league.getIdLeague();
        nameLeague = league.getNameLeague();
        logoLeague = league.getLogoLeague();
    }


    @Exclude
    @Override
    public String getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(String idLeague) {
        this.idLeague = idLeague;
    }

    @Override
    public String getNameLeague() {
        return nameLeague;
    }

    public void setNameLeague(String nameLeague) {
        this.nameLeague = nameLeague;
    }

    @Override
    public String getLogoLeague() {
        return logoLeague;
    }

    public void setLogoLeague(String logoLeague) {
        this.logoLeague = logoLeague;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof LeagueEntity)) return false;
        LeagueEntity o = (LeagueEntity) obj;
        return o.getIdLeague()==(this.getIdLeague());
    }

    @Override
    public String toString() {
        return nameLeague;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nameLeague", nameLeague);
        result.put("logoLeague", logoLeague);
        return result;
    }
}

