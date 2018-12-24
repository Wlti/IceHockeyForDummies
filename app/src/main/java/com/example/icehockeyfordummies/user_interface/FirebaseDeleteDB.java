package com.example.icehockeyfordummies.user_interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.icehockeyfordummies.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class FirebaseDeleteDB extends AppCompatActivity {
private FirebaseDatabase mDatabase;
private DatabaseReference mDatabaseRef;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.firebase);


    // Initialize the Firebase Realtime Databse variables
    mDatabase = FirebaseDatabase.getInstance();
    mDatabaseRef = mDatabase.getReference();
    mDatabaseRef.child("leagues").removeValue();
    mDatabaseRef.child("clubs").removeValue();
    mDatabaseRef.child("players").removeValue();
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