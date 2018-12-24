package com.example.icehockeyfordummies.database.Entity;

import android.support.annotation.NonNull;

import com.example.icehockeyfordummies.models.Player;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

public class PlayerEntity implements Player {
    private String idPlayer;
    private String numberPlayer;
    private String firstNamePlayer;
    private String lastNamePlayer;
    private String birthdatePlayer;
    private String picturePlayer;
    private String positionPlayer;
    private String licensePlayer;
    private String pointsPlayer;
    private String FK_idClub;

    public PlayerEntity() {
    }

    public PlayerEntity(@NonNull String numberPlayer, @NonNull String firstNamePlayer, @NonNull String lastNamePlayer,
                        @NonNull String birthdatePlayer, String picturePlayer, @NonNull String positionPlayer, @NonNull String licensePlayer,
                        String pointsPlayer, @NonNull String idClub) {

        this.numberPlayer = numberPlayer;
        this.firstNamePlayer = firstNamePlayer;
        this.lastNamePlayer = lastNamePlayer;
        this.birthdatePlayer = birthdatePlayer;
        this.picturePlayer = picturePlayer;
        this.positionPlayer = positionPlayer;
        this.licensePlayer = licensePlayer;
        this.pointsPlayer = pointsPlayer;
        FK_idClub = idClub;
    }

    public PlayerEntity(Player player) {
        idPlayer = player.getIdPlayer();
        firstNamePlayer = player.getFirstNamePlayer();
        lastNamePlayer = player.getLastNamePlayer();
        birthdatePlayer = player.getBirthdatePlayer();
        numberPlayer = player.getNumberPlayer();
        picturePlayer = player.getPicturePlayer();
        pointsPlayer = player.getPointsPlayer();
        positionPlayer = player.getPositionPlayer();
        licensePlayer = player.getLicensePlayer();
        FK_idClub = player.getFK_idClub();
    }


    @Exclude
    @Override
    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    @Override
    public String getFirstNamePlayer() {
        return firstNamePlayer;
    }

    public void setFirstNamePlayer(String firstNamePlayer) {
        this.firstNamePlayer = firstNamePlayer;
    }

    @Override
    public String getLastNamePlayer() {
        return lastNamePlayer;
    }

    public void setLastNamePlayer(String lastNamePlayer) {
        this.lastNamePlayer = lastNamePlayer;
    }
    @Override
    public String getBirthdatePlayer() {
        return birthdatePlayer;
    }

    public void setBirthdatePlayer(String birthdatePlayer) {
        this.birthdatePlayer = birthdatePlayer;
    }
    @Override
    public String getNumberPlayer() {
        return numberPlayer;
    }

    public void setNumberPlayer(String numberPlayer) {
        this.numberPlayer = numberPlayer;
    }

    @Override
    public String getPicturePlayer() {
        return picturePlayer;
    }

    public void setPicturePlayer(String picturePlayer) {
        this.picturePlayer = picturePlayer;
    }
    @Override
    public String getPointsPlayer() {
        return pointsPlayer;
    }

    public void setPointsPlayer(String pointsPlayer) {
        this.pointsPlayer = pointsPlayer;
    }

    @Override
    public String getPositionPlayer() {
        return positionPlayer;
    }

    public void setPositionPlayer(String positionPlayer) {
        this.positionPlayer = positionPlayer;
    }

    @Override
    public String getFK_idClub() {
        return FK_idClub;
    }

    public void setFK_idClub(String FK_idClubF) {
        this.FK_idClub = FK_idClub;
    }
    @Override
    public String getLicensePlayer() {
        return licensePlayer;
    }

    public void setLicensePlayer(String licensePlayer) {
        this.licensePlayer = licensePlayer;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof PlayerEntity)) return false;
        PlayerEntity o = (PlayerEntity) obj;
        return o.getIdPlayer()==(this.getIdPlayer());
    }

    @Override
    public String toString() {
        return lastNamePlayer;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstname", firstNamePlayer);
        result.put("lastname", lastNamePlayer);
        result.put("number", numberPlayer);
        result.put("position", positionPlayer);
        result.put("points", pointsPlayer);
        result.put("license", licensePlayer);
        result.put("birthdate", birthdatePlayer);
        result.put("picture", picturePlayer);
        result.put("FK_idClub", FK_idClub);

        return result;
    }
}
