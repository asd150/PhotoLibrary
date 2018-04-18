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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AlbumUsers albumUsers;
    private User user;
    private int currentUserIndex =0;
    private ListView listView;
    private ArrayList<Album> albumList;
    private ArrayAdapter<Album> arrayAdapter;
    private FloatingActionButton addbutton;
    private Button rename;
    private RelativeLayout layout;
    private  FloatingActionButton delbtn;
    private FloatingActionButton search;
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
       layout = (RelativeLayout) findViewById(R.id.layoutid);
       layout.setClickable(true);
        listView = (ListView) findViewById(R.id.photo_list);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        arrayAdapter = new ArrayAdapter<Album>(this,R.layout.albumsel,user.getAlbums());
        listView.setAdapter(arrayAdapter);

        search = (FloatingActionButton) findViewById(R.id.searchTags);

        addbutton = (FloatingActionButton) findViewById(R.id.addButton);
        rename = (Button) findViewById(R.id.rename);
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
               delbtn = (FloatingActionButton) findViewById(R.id.deleteButton);
                delbtn.setVisibility(View.VISIBLE);
                rename.setVisibility(View.VISIBLE);
                search.setVisibility(View.INVISIBLE);
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
                                search.setVisibility(View.VISIBLE);
                                albumUsers.saveToDisk(MainActivity.this);
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                delbtn.setVisibility(View.INVISIBLE);
                                rename.setVisibility(View.INVISIBLE);
                                search.setVisibility(View.VISIBLE);
                                addbutton.setVisibility(View.VISIBLE);
                            }
                        });
                        builder.show();
                    }
                });

                rename.setOnClickListener(new View.OnClickListener() {
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
                                    delbtn.setVisibility(View.INVISIBLE);
                                    rename.setVisibility(View.INVISIBLE);
                                    search.setVisibility(View.VISIBLE);
                                    addbutton.setVisibility(View.VISIBLE);
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
                                        Album album = user.getAlbums().get(position);
                                        album.setAlbumName(input.getText().toString());

                                        albumUsers.saveToDisk(MainActivity.this);

                                    }

                                }
                                delbtn.setVisibility(View.INVISIBLE);
                                rename.setVisibility(View.INVISIBLE);
                                search.setVisibility(View.VISIBLE);
                                addbutton.setVisibility(View.VISIBLE);
                            }

                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delbtn.setVisibility(View.INVISIBLE);
                                rename.setVisibility(View.INVISIBLE);
                                search.setVisibility(View.VISIBLE);
                                addbutton.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }
                        });

                        builder.show();

                    }
                });
                return true;
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


       layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

                   delbtn.setVisibility(View.INVISIBLE);
                   rename.setVisibility(View.INVISIBLE);
               search.setVisibility(View.VISIBLE);
                   addbutton.setVisibility(View.VISIBLE);

           }
       });

       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                if(user.getAlbums().size()>0){
                    Bundle bundle = new Bundle();
                    bundle.putInt(searchTags.USER_INDEX,0);

                    Intent intent = new Intent(MainActivity.this,searchTags.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
           }
       });
    }

}
