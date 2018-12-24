package com.example.icehockeyfordummies.user_interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.icehockeyfordummies.R;


public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.settings);
        }

    //Naviguer vers about
    public void toAbout(View view) {
        Intent intent = new Intent(SettingsActivity.this,AboutActivity.class);
        startActivity(intent);
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
