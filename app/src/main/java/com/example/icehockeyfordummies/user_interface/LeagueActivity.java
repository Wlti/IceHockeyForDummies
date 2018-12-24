package com.example.icehockeyfordummies.user_interface;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.icehockeyfordummies.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

//Show the list of the leagues
public class LeagueActivity extends AppCompatActivity {

    Button btnAdd;
    Button btnEdit;
    EditText addLeague;
    EditText editThisLeague;
    EditText newleague;
    ListView listLeague;

    //test si une league existe déjà
    boolean exist;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = db.getReference();
    DatabaseReference leaguesRef = rootRef.child("leagues");
    DatabaseReference clubsRef = rootRef.child("clubs");
    DatabaseReference playersRef = rootRef.child("players");

    ArrayList<String> keys = new ArrayList<String>();
    ArrayList<String> leaguesArrayList = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    ArrayList<String> clubKeys = new ArrayList<String>();
    ArrayList<String> clubsArrayList = new ArrayList<String>();
    ArrayList<String> values= new ArrayList<>();

    ArrayList<String> playersKey = new ArrayList<String>();
    ArrayList<String> playersArrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.league);

        btnAdd = findViewById(R.id.btn_add_league);
        btnEdit = findViewById(R.id.btn_update_league);
        editThisLeague = findViewById(R.id.edit_league);
        newleague = findViewById(R.id.edit_league2);
        addLeague = findViewById(R.id.add_league);
        listLeague = findViewById(R.id.list_view_league);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, leaguesArrayList);
        listLeague.setAdapter(arrayAdapter);

        //Add a league
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String leagueToAdd = addLeague.getText().toString();

                //Tester s'il n'existe pas déjà une league avec ce nom
                rootRef.child("leagues").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean exist = false;
                        for (int i = 0; i < keys.size(); i++) {
                            String value="";
                            value = dataSnapshot.child(keys.get(i)).getValue().toString();
                            if (value.equals(leagueToAdd)) {
                                exist=true;
                            }}
                    if(!exist){
                        if(!leagueToAdd.equals(""))
                        {
                            leaguesRef.push().setValue(leagueToAdd);
                            addLeague.setText("");

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


        //Update league
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //récupération des données
                String leagueToUpdate = editThisLeague.getText().toString();
                String nl = newleague.getText().toString();

                //Test si les deux cases sont bien remplies
                if(!leagueToUpdate.equals("")&& !nl.equals("")){

                //Tester s'il n'existe pas déjà une league avec ce nom
                rootRef.child("leagues").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        exist = false;
                        for (int i = 0; i < keys.size(); i++) {
                            String value="";
                            value = dataSnapshot.child(keys.get(i)).getValue().toString();
                            if (value.equals(nl)) {
                                exist=true;
                            }}
                        if(!exist){
                            //Si elle n'existe pas déjà, la créer
                                for (int i = 0; i < keys.size(); i++) {
                                    String value="";
                                    value = dataSnapshot.child(keys.get(i)).getValue().toString();
                                    if (value.equals(leagueToUpdate)) {
                                        rootRef.child("leagues").child(keys.get(i)).setValue(nl);
                                    }}
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


                    //Update clubs
                    clubsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!exist){
                            if(dataSnapshot.child(leagueToUpdate).exists()) {
                                Iterable<DataSnapshot> snap = dataSnapshot.child(leagueToUpdate).getChildren();


                                //Remove
                                    clubsRef.child(leagueToUpdate).removeValue();


                                //Récupérer les données
                                for (DataSnapshot ds : snap) {
                                    String c = ds.getValue(String.class);
                                    values.add(c);
                                }

                                //Push.set
                                for (int j = 0; j < values.size(); j++) {
                                    clubsRef.child(nl).push().setValue(values.get(j));
                                }
                            }
                        }}
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                    //Update players
                    playersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        ArrayList<String > val;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!exist) {
                                    for (int i = 0; i < values.size(); i++) {
                                        val = new ArrayList<String>();
                                        if (dataSnapshot.child(leagueToUpdate).child(values.get(i)).exists()) {
                                            Iterable<DataSnapshot> snap = dataSnapshot.child(leagueToUpdate).child(values.get(i)).getChildren();

                                            //Remove
                                            playersRef.child(leagueToUpdate).removeValue();


                                            //Récupérer les données
                                            for (DataSnapshot ds : snap) {
                                                String c = ds.getValue(String.class);
                                                val.add(c);
                                            }

                                            //Push.set
                                            for (int j = 0; j < val.size(); j++) {
                                                playersRef.child(nl).child(values.get(i)).push().setValue(val.get(j));
                                            }
                                        }
                                    }
                                }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

            }}});


        //delete a club
        listLeague.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(adapterView.getContext());
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete  '" + arrayAdapter.getItem(i).toString() + "'?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String message = listLeague.getItemAtPosition(i).toString();
                        db.getReference("clubs").child(message).removeValue();
                        db.getReference("players").child(message).removeValue();
                        rootRef.child("leagues").child(keys.get(i)).removeValue();
                        leaguesArrayList.remove(i);
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

        //go to ClubActivity
        listLeague.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LeagueActivity.this, ClubActivity.class);
                String message = listLeague.getItemAtPosition(position).toString();
                intent.putExtra("league", message);
                startActivity(intent);
            }
            });


        //modify the lists
        leaguesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String val = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey();
                leaguesArrayList.add(val); keys.add(key);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String val = dataSnapshot.getValue().toString();
                Integer ind = keys.indexOf(dataSnapshot.getKey());
                leaguesArrayList.set(ind,val);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                Integer ind = keys.indexOf(key);
                leaguesArrayList.remove(ind); keys.remove(ind);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //modify the lists
        clubsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String val = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey();
                clubsArrayList.add(val); clubKeys.add(key);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String val = dataSnapshot.getValue().toString();
                Integer ind = clubKeys.indexOf(dataSnapshot.getKey());
                clubsArrayList.set(ind,val);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                Integer ind = clubKeys.indexOf(key);
                clubsArrayList.remove(ind); clubKeys.remove(ind);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //modify the lists
        playersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String val = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey();
                playersArrayList.add(val); playersKey.add(key);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String val = dataSnapshot.getValue().toString();
                Integer ind = playersKey.indexOf(dataSnapshot.getKey());
                playersArrayList.set(ind,val);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                Integer ind = playersKey.indexOf(key);
                playersArrayList.remove(ind); playersKey.remove(ind);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
