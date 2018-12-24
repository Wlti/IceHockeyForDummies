package com.example.icehockeyfordummies.user_interface;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.icehockeyfordummies.R;
import com.example.icehockeyfordummies.models.Club;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private DrawerLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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



    //Naviguer vers les settings
    public void toSettings(View view) {
        Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(intent);
    }


    //Naviguer vers FirebaseActivity
    public void toFirebase(View view) {
        Intent intent = new Intent(MainActivity.this,FirebaseActivity.class);
        startActivity(intent);
    }

    //Naviguer vers DeleteFirebase
    public void toDeleteFirebase(View view) {
        Intent intent = new Intent(MainActivity.this,FirebaseDeleteDB.class);
        startActivity(intent);
    }

    //Naviguer vers Firebaseleagues
    public void toFirebaseLeagues(View view) {
        Intent intent = new Intent(MainActivity.this,LeagueActivity.class);
        startActivity(intent);
    }
}