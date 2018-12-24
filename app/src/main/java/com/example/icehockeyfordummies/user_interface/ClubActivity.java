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
import android.widget.TextView;

import com.example.icehockeyfordummies.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

//Show the list of the clubs of a league
public class ClubActivity extends AppCompatActivity {
    Button btnAdd;
    EditText addClub;
    ListView listClub;
    String name;
    Button btnEdit;
    EditText editThisClub;
    EditText newClub;
    TextView fromLeague;

    //pour tester si un club du même nom existe déjà
    boolean exist;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = db.getReference();
    DatabaseReference clubsRef ;
    DatabaseReference playersRef = rootRef.child("players");

    ArrayList<String> keys = new ArrayList<String>();
    ArrayList<String> clubsArrayList = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    ArrayList<String> playersKey = new ArrayList<String>();
    ArrayList<String> playersArrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club);

        this.name =  getIntent().getStringExtra("league");
        this.clubsRef = rootRef.child("clubs").child(name);
        addClub = findViewById(R.id.add_club);
        listClub = findViewById(R.id.list_view_club);
        btnAdd = findViewById(R.id.btn_add_club);
        btnEdit = findViewById(R.id.btn_update_club);
        editThisClub = findViewById(R.id.edit_club);
        newClub = findViewById(R.id.edit_club2);
        fromLeague = findViewById(R.id.fromLeague);
        fromLeague.setText(name);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clubsArrayList);
        listClub.setAdapter(arrayAdapter);

        //Add a club
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubToAdd = addClub.getText().toString();

                //Tester s'il n'existe pas déjà un club avec ce nom
                rootRef.child("clubs").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        exist = false;
                        for (int i = 0; i < keys.size(); i++) {
                            String value="";
                            value = dataSnapshot.child(keys.get(i)).getValue().toString();
                            if (value.equals(clubToAdd)) {
                                exist=true;
                            }}
                        if(!exist){

                                if(!clubToAdd.equals(""))
                                {
                                    clubsRef.push().setValue(clubToAdd);
                                    addClub.setText("");
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

        //update a club
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubToUpdate = editThisClub.getText().toString();
                String nc = newClub.getText().toString();

                if(!clubToUpdate.equals("")&& !nc.equals("")) {
                    rootRef.child("clubs").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Tester si un club du même nom existe déjà
                            exist = false;
                            for (int i = 0; i < keys.size(); i++) {
                                String value="";
                                value = dataSnapshot.child(keys.get(i)).getValue().toString();
                                if (value.equals(nc)) {
                                    exist=true;
                                }}
                            if(!exist){
                                for (int i = 0; i < keys.size(); i++) {
                                    String value = "";
                                    value = dataSnapshot.child(keys.get(i)).getValue().toString();
                                    if (value.equals(clubToUpdate)) {
                                        rootRef.child("clubs").child(name).child(keys.get(i)).setValue(nc);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                    //Update players
                    playersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        ArrayList<String> values;

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            values = new ArrayList<String>();
                            if(!exist){


                            if (dataSnapshot.child(name).child(clubToUpdate).exists()) {
                                Iterable<DataSnapshot> snap = dataSnapshot.child(name).child(clubToUpdate).getChildren();


                                //Remove
                                playersRef.child(name).child(clubToUpdate).removeValue();


                                //Récupérer les données
                                for (DataSnapshot ds : snap) {
                                    String c = ds.getValue(String.class);
                                    values.add(c);
                                }

                                //Push.set
                                for (int j = 0; j < values.size(); j++) {
                                    playersRef.child(name).child(nc).push().setValue(values.get(j));
                                }
                            }
                        }}

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }}});


        //delete a club
        listClub.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(adapterView.getContext());
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete  '" + arrayAdapter.getItem(i).toString() + "'?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String message = listClub.getItemAtPosition(i).toString();
                        db.getReference("players").child(name).child(message).removeValue();
                        rootRef.child("clubs").child(name).child(keys.get(i)).removeValue();
                        clubsArrayList.remove(i);
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

        //go to TeamActivity
        listClub.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClubActivity.this, TeamActivity.class);
                String message = listClub.getItemAtPosition(position).toString();
                intent.putExtra("league",name);
                intent.putExtra("club", message);
                startActivity(intent);
            }
        });

        //modify the lists
        clubsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String val = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey();
                clubsArrayList.add(val); keys.add(key);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String val = dataSnapshot.getValue().toString();
                Integer ind = keys.indexOf(dataSnapshot.getKey());
                clubsArrayList.set(ind,val);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                Integer ind = keys.indexOf(key);
                clubsArrayList.remove(ind); keys.remove(ind);
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
