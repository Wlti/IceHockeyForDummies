package com.example.icehockeyfordummies;

import android.app.Application;
import com.example.icehockeyfordummies.database.Repository.ClubRepository;
import com.example.icehockeyfordummies.database.Repository.LeagueRepository;
import com.example.icehockeyfordummies.database.Repository.PlayerRepository;



public class BaseApp extends Application {
    public ClubRepository getClubRepository() {
        return ClubRepository.getInstance();
    }
    public LeagueRepository getLeagueRepository() {
        return LeagueRepository.getInstance();
    }
    public PlayerRepository getPlayerRepository() {
        return PlayerRepository.getInstance();
    }
}