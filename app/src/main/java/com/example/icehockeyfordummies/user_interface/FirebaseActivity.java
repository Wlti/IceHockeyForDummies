package com.example.icehockeyfordummies.user_interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.icehockeyfordummies.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class FirebaseActivity extends AppCompatActivity {


    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase);


        // Initialize the Firebase Realtime Databse variables
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference();


        //League 1
        mDatabaseRef.child("leagues").push().setValue("National League");

        //League 2
        mDatabaseRef.child("leagues").push().setValue("Swiss League");

        //League 3
        mDatabaseRef.child("leagues").push().setValue("MySports League");

        //League 4
        mDatabaseRef.child("leagues").push().setValue("Regio League");


        //Club 1
        mDatabaseRef.child("clubs").child("National League").push().setValue("Fribourg-Gotteron");

        //Club 2
        mDatabaseRef.child("clubs").child("National League").push().setValue("SC Bern");

        //Club 3
        mDatabaseRef.child("clubs").child("Swiss League").push().setValue("EHC Olten");

        //Club 4
        mDatabaseRef.child("clubs").child("Swiss League").push().setValue("EHC Visp");

        //Club 5
        mDatabaseRef.child("clubs").child("MySports League").push().setValue("HC Valais-Chablais");

        //Club 6
        mDatabaseRef.child("clubs").child("MySports League").push().setValue("HC Sierre");

        //Club 7
        mDatabaseRef.child("clubs").child("Regio League").push().setValue("HC Moutier");

        //Club 8
        mDatabaseRef.child("clubs").child("Regio League").push().setValue("HC Prilly");


        //Player 1
        mDatabaseRef.child("players").child("National League").child("Fribourg-Gotteron").push().setValue("#20 Reto Berra 1987 Goalie");

        //Player 2
        mDatabaseRef.child("players").child("National League").child("Fribourg-Gotteron").push().setValue("#2 Marc Abplanalp 1984 Defense");

        //Player 3
        mDatabaseRef.child("players").child("National League").child("Fribourg-Gotteron").push().setValue("#33 Marco Forrer 1996 Defense");

        //Player 4
        mDatabaseRef.child("players").child("National League").child("Fribourg-Gotteron").push().setValue("#11 Lukas Lhotak 1993 Winger");

        //Player 5
        mDatabaseRef.child("players").child("National League").child("Fribourg-Gotteron").push().setValue("#16 Michael Birner 1986 Winger");

        //Player 6
        mDatabaseRef.child("players").child("National League").child("SC Bern").push().setValue("#30 Leonardo Genoni 1987 Goalie");

        //Player 7
        mDatabaseRef.child("players").child("National League").child("SC Bern").push().setValue("#2 Beat Gerber 1982 Defense");

        //Player 8
        mDatabaseRef.child("players").child("National League").child("SC Bern").push().setValue("#77 Yanik Burren 1997 Defense");

        //Player 9
        mDatabaseRef.child("players").child("National League").child("SC Bern").push().setValue("#9 Jan Mursak 1988 Winger");

        //Player 10
        mDatabaseRef.child("players").child("National League").child("SC Bern").push().setValue("#21 Simon Moser 1989 Winger");

        //Player 11
        mDatabaseRef.child("players").child("Swiss League").child("EHC Olten").push().setValue("#35 Matthias Mischler 1990 Goalie");

        //Player 12
        mDatabaseRef.child("players").child("Swiss League").child("EHC Olten").push().setValue("#8 Simon Luethi 1986 Defense");

        //Player 13
        mDatabaseRef.child("players").child("Swiss League").child("EHC Olten").push().setValue("#19 Tim Bucher 1988 Defense");

        //Player 14
        mDatabaseRef.child("players").child("Swiss League").child("EHC Olten").push().setValue("#7 Cason Hohmann 1993 Winger");

        //Player 15
        mDatabaseRef.child("players").child("Swiss League").child("EHC Olten").push().setValue("#10 Ueli Huber 1995 Winger");

        //Player 16
        mDatabaseRef.child("players").child("Swiss League").child("EHC Visp").push().setValue("#26 Reto Lory 1988 Goalie");

        //Player 17
        mDatabaseRef.child("players").child("Swiss League").child("EHC Visp").push().setValue("#17 Jens Nater 1995 Defense");

        //Player 18
        mDatabaseRef.child("players").child("Swiss League").child("EHC Visp").push().setValue("#27 Marc Steiner 1996 Defense");

        //Player 19
        mDatabaseRef.child("players").child("Swiss League").child("EHC Visp").push().setValue("#9 Olivier Achermann 1994 Winger");

        //Player 20
        mDatabaseRef.child("players").child("Swiss League").child("EHC Visp").push().setValue("#13 Niki Altorfer 1990 Winger");

        //Player 21
        mDatabaseRef.child("players").child("MySports League").child("HC Valais-Chablais").push().setValue("#82 Maxime Baud 1997 Goalie");

        //Player 22
        mDatabaseRef.child("players").child("MySports League").child("HC Valais-Chablais").push().setValue("#13 Jimmy Oudelet 1992 Defense");

        //Player 23
        mDatabaseRef.child("players").child("MySports League").child("HC Valais-Chablais").push().setValue("#47 Thomas Cheseaux 1995 Defense");

        //Player 24
        mDatabaseRef.child("players").child("MySports League").child("HC Valais-Chablais").push().setValue("#18 Jeremy Gailland 1988 Winger");

        //Player 25
        mDatabaseRef.child("players").child("MySports League").child("HC Valais-Chablais").push().setValue("#21 Romain Seydoux 1994 Winger");

        //Player 26
        mDatabaseRef.child("players").child("MySports League").child("HC Sierre").push().setValue("#30 Remo Giovannini 1991 Goalie");

        //Player 27
        mDatabaseRef.child("players").child("MySports League").child("HC Sierre").push().setValue("#4 Julien Massy 1998 Defense");

        //Player 28
        mDatabaseRef.child("players").child("MySports League").child("HC Sierre").push().setValue("#7 Eliott Meyrat 1994 Defense");

        //Player 29
        mDatabaseRef.child("players").child("MySports League").child("HC Sierre").push().setValue("#8 Romain Wyssen 1993 Winger");

        //Player 30
        mDatabaseRef.child("players").child("MySports League").child("HC Sierre").push().setValue("#10 Arthur Devouassoux 1988 Winger");

        //Player 31
        mDatabaseRef.child("players").child("Regio League").child("HC Moutier").push().setValue("#31 Thierry Wermeille 1996 Goalie");

        //Player 32
        mDatabaseRef.child("players").child("Regio League").child("HC Moutier").push().setValue("#6 Nelson Boldini 1996 Defense");

        //Player 33
        mDatabaseRef.child("players").child("Regio League").child("HC Moutier").push().setValue("#17 Mael Seuret 1999 Defense");

        //Player 34
        mDatabaseRef.child("players").child("Regio League").child("HC Moutier").push().setValue("#4 Corentin Berthoud 1989 Winger");

        //Player 35
        mDatabaseRef.child("players").child("Regio League").child("HC Moutier").push().setValue("#26 Karim Charpie 2000 Winger");

        //Player 36
        mDatabaseRef.child("players").child("Regio League").child("HC Prilly").push().setValue("#96 Romain Martin 1996 Goalie");

        //Player 37
        mDatabaseRef.child("players").child("Regio League").child("HC Prilly").push().setValue("#4 Nicolas Villa 1989 Defense");

        //Player 38
        mDatabaseRef.child("players").child("Regio League").child("HC Prilly").push().setValue("#88 Kilian Chetelat 1993 Defense");

        //Player 39
        mDatabaseRef.child("players").child("Regio League").child("HC Prilly").push().setValue("#1 Dylan Cordey 1992 Winger");

        //Player 40
        mDatabaseRef.child("players").child("Regio League").child("HC Prilly").push().setValue("#10 Kevin Demierre 1993 Winger");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_home :
                Intent toHome = new Intent(this,MainActivity.class);
                startActivity(toHome);
                return true;
            case R.id.action_settings :
                Intent toSettings = new Intent(this,SettingsActivity.class);
                startActivity(toSettings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

