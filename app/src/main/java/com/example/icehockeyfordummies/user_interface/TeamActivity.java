package com.example.icehockeyfordummies.user_interface;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.icehockeyfordummies.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

//Show the list of players of a club of a league
public class TeamActivity extends AppCompatActivity {
    Button btnAdd;
    EditText addPlayer;
    ListView listPlayers;
    String name;
    String nameLeague;
    TextView fromL;
    TextView fromC;

    //Tester si un player avec ce nom existe déjà
    boolean exist;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = db.getReference();
    DatabaseReference clubsRef ;
    ArrayList<String> keys = new ArrayList<String>();
    ArrayList<String> playersArrayList = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team);
        this.name =  getIntent().getStringExtra("club");
        this.nameLeague =  getIntent().getStringExtra("league");
        this.clubsRef = rootRef.child("players").child(nameLeague).child(name);
        addPlayer = findViewById(R.id.add_player);
        listPlayers = findViewById(R.id.list_view_player);
        btnAdd = findViewById(R.id.btn_add_player);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playersArrayList);
        listPlayers.setAdapter(arrayAdapter);

        //Titre
        fromC = findViewById(R.id.fromC);
        fromL = findViewById(R.id.fromL);
        fromC.setText(name);
        fromL.setText(nameLeague);

        //Add a player
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerToAdd = addPlayer.getText().toString();
                //Tester s'il n'existe pas déjà un club avec ce nom
                rootRef.child("players").child(nameLeague).child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        exist = false;
                        for (int i = 0; i < keys.size(); i++) {
                            String value="";
                            value = dataSnapshot.child(keys.get(i)).getValue().toString();
                            if (value.equals(playerToAdd)) {
                                exist=true;
                            }}
                        if(!exist){
                            if(!playerToAdd.equals(""))
                            {
                                clubsRef.push().setValue(playerToAdd);
                                addPlayer.setText("");
                                InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        //Delete a player
        listPlayers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(adapterView.getContext());
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete  '" + arrayAdapter.getItem(i).toString() + "'?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rootRef.child("players").child(nameLeague).child(name).child(keys.get(i)).removeValue();
                        playersArrayList.remove(i);
                        keys.remove(i);
                        arrayAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                try {
                    alert.show();
                } catch (Exception ex) {
                    Log.d("err", ex.getMessage());
                }
                return true;
            }
        });

        //Update a player
        listPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String playerToUpdate = listPlayers.getItemAtPosition(position).toString();
                updatePlayer(playerToUpdate);
            }
        });




        //Modify the lists
        clubsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String val = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey();
                playersArrayList.add(val); keys.add(key);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String val = dataSnapshot.getValue().toString();
                Integer ind = keys.indexOf(dataSnapshot.getKey());
                playersArrayList.set(ind,val);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                Integer ind = keys.indexOf(key);
                playersArrayList.remove(ind); keys.remove(ind);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void updatePlayer (String n){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dial = inflater.inflate(R.layout.update,null);
        dialog.setView(dial);
        final EditText newPlayer = (EditText) dial.findViewById(R.id.editText);
        final Button button = (Button) dial.findViewById(R.id.button2);
        dialog.setTitle("Update the player "+n);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        //Edit a player
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String playerToUpdate = n;

                String np = newPlayer.getText().toString();
                if(!playerToUpdate.equals("")&& !np.equals("")) {
                    rootRef.child("players").child(nameLeague).child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Tester s'il existe un autre joueur avec ce nom
                            boolean exist = false;
                            for (int i = 0; i < keys.size(); i++) {
                                String value="";
                                value = dataSnapshot.child(keys.get(i)).getValue().toString();
                                if (value.equals(np)) {
                                    exist=true;
                                }}
                            if(!exist){
                                for (int i = 0; i < keys.size(); i++) {
                                    String value = "";
                                    value = dataSnapshot.child(keys.get(i)).getValue().toString();
                                    if (value.equals(playerToUpdate)) {
                                        rootRef.child("players").child(nameLeague).child(name).child(keys.get(i)).setValue(np);
                                    }
                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
                alertDialog.dismiss();
            }});



    }


    //Menu
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
