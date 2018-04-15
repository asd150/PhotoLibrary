package com.example.desai.app43;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AlbumUsers albumUsers;
    private User user;
    private ListView listView;
    private ArrayList<Album> albumList;
    private ArrayAdapter<Album> arrayAdapter;
    private FloatingActionButton addbutton;
    private final static String INDEX = "index";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //read users
        albumUsers =AlbumUsers.loadFromDisk(this);
       if(albumUsers==null){
           albumUsers = new AlbumUsers();
            User u = new User("Arth");
            albumUsers.addUsers(u);
            user = albumUsers.getUsers().get(0);
        }
        else{
            user = albumUsers.getUsers().get(0);
        }
        albumList = user.getAlbums();

        listView = (ListView) findViewById(R.id.photo_list);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        arrayAdapter = new ArrayAdapter<Album>(this,R.layout.albumsel,user.getAlbums());
        listView.setAdapter(arrayAdapter);

        addbutton = (FloatingActionButton) findViewById(R.id.addButton);
        addbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Enter album name.");
                        final EditText input = new EditText(MainActivity.this);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);

                        builder.setView(input);



                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean notexists = true;
                        String getInput = input.getText().toString();

                       if(getInput.isEmpty()){
                        return;
                       }
                       else {


                            for(int i =0; i < user.getAlbums().size();i++){
                                if(user.getAlbums().get(i).getAlbumName().equals(getInput)){
                                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(MainActivity.this);
                                    alert.setTitle("Album Exists");
                                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    notexists = false;
                                }

                            }
                            if(notexists){
                                Album album = new Album(getInput);
                                user.addAlbum(album);
                                albumUsers.saveToDisk(MainActivity.this);
                            }

                       }
                    }

                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });

      //albumUsers.saveToDisk(this);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override

            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final FloatingActionButton delbtn = (FloatingActionButton) findViewById(R.id.deleteButton);
                delbtn.setVisibility(View.VISIBLE);
                addbutton.setVisibility(View.INVISIBLE);

                delbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = position;
                        final Album name = arrayAdapter.getItem(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Are you sure you want to delete album "+arrayAdapter.getItem(position).getAlbumName()+"?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                user.getAlbums().remove(position);
                                listView.setAdapter(arrayAdapter);
                                delbtn.setVisibility(View.INVISIBLE);
                                addbutton.setVisibility(View.VISIBLE);
                                albumUsers.saveToDisk(MainActivity.this);
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                delbtn.setVisibility(View.INVISIBLE);
                                addbutton.setVisibility(View.VISIBLE);
                            }
                        });
                        builder.show();
                    }
                });

                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Log.d("INDEX IN MAIN",position+"");
                bundle.putInt("INDEX", position);
                Intent intent = new Intent(MainActivity.this,OpenAlbum.class);
                intent.putExtras(bundle);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


}
