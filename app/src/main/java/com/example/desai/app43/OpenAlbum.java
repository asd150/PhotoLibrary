package com.example.desai.app43;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class OpenAlbum extends AppCompatActivity {

    private User user;
    private Album album;
    private AlbumUsers albumUsers;
    private ArrayList<Photos> photosArrayList;
    private ThumbAdapter thumbAdapter;
    private GridView gridView;
    private FloatingActionButton addButton;
    private FloatingActionButton deleteButton;
    private Button moveButton;
    private final int SELECT_PHOTO = 1;
    private int AlbumIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        int index=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thumbnail);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            index = bundle.getInt("INDEX");
            AlbumIndex = index;
        }


        albumUsers = AlbumUsers.loadFromDisk(OpenAlbum.this);
        user = albumUsers.getUsers().get(0);
        album = user.getAlbums().get(index);
        Log.d("TOTAL",album.getListofphotos().size()+"");

        photosArrayList = (ArrayList<Photos>) album.getListofphotos();
        Log.d("INDEX FOUND",photosArrayList.size()+"");

        thumbAdapter = new ThumbAdapter(OpenAlbum.this, (ArrayList<Photos>) album.getListofphotos());
        //thumbAdapter = new ThumbAdapter();

        gridView = (GridView) findViewById(R.id.searchGrid);

        gridView.setAdapter(thumbAdapter);

      moveButton = (Button) findViewById(R.id.moveButton);
      moveButton.setVisibility(View.INVISIBLE);
      addButton = (FloatingActionButton) findViewById(R.id.addAlbum);
      deleteButton = (FloatingActionButton) findViewById(R.id.deletePhoto);

        deleteButton.setVisibility(View.INVISIBLE);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PHOTO);
                Log.d("LIST OPENED 1 ",""+album.getListofphotos().size());
                albumUsers.saveToDisk(OpenAlbum.this);
                thumbAdapter = new ThumbAdapter(OpenAlbum.this, (ArrayList) album.getListofphotos());
                gridView.setAdapter(thumbAdapter);
            }



        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position;
                Log.d("IMAGE",index+"");

                Intent intent = new Intent(OpenAlbum.this,OpenPhoto.class);
                intent.putExtra("ALBUMINDEX",AlbumIndex);
                intent.putExtra("PHOTOINDEX",position);
                startActivity(intent);
            }
        });


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int selectedPhoto = position;
                deleteButton.setVisibility(View.VISIBLE);
                moveButton.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.INVISIBLE);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(OpenAlbum.this);
                        alert.setTitle("Delete Photo");
                        alert.setMessage("Are You Sure?");

                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               Photos p = album.getListofphotos().get(selectedPhoto);
                                album.removePhoto(p);
                                thumbAdapter = new ThumbAdapter(OpenAlbum.this, (ArrayList) album.getListofphotos());
                                gridView.setAdapter(thumbAdapter);
                                albumUsers.saveToDisk(OpenAlbum.this);
                                addButton.setVisibility(View.VISIBLE);
                                deleteButton.setVisibility(View.INVISIBLE);
                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                deleteButton.setVisibility(View.INVISIBLE);
                                moveButton.setVisibility(View.INVISIBLE);
                                addButton.setVisibility(View.VISIBLE);
                            }
                        });


                        alert.show();
                    }
                });


                moveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(OpenAlbum.this);
                        builder.setTitle("Enter album name.");
                        final EditText input = new EditText(OpenAlbum.this);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);

                        builder.setView(input);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                boolean notexists = false;
                                Album album1 =null;
                                String getInput = input.getText().toString();

                                if(getInput.isEmpty()){
                                    return;
                                }
                                else {


                                    for(int i =0; i < user.getAlbums().size();i++){
                                        if(user.getAlbums().get(i).getAlbumName().equals(getInput)){
                                            notexists = true;
                                            album1 = user.getAlbums().get(i);
                                        }

                                    }
                                    if(notexists) {
                                        album1.addPhoto(album.getListofphotos().get(position));
                                        album.getListofphotos().remove(position);

                                        thumbAdapter = new ThumbAdapter(OpenAlbum.this, (ArrayList) album.getListofphotos());
                                        gridView.setAdapter(thumbAdapter);
                                        deleteButton.setVisibility(View.INVISIBLE);
                                        moveButton.setVisibility(View.INVISIBLE);
                                        addButton.setVisibility(View.VISIBLE);
                                        albumUsers.saveToDisk(OpenAlbum.this);
                                    }


                                }
                            }

                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteButton.setVisibility(View.INVISIBLE);
                                moveButton.setVisibility(View.INVISIBLE);
                                addButton.setVisibility(View.VISIBLE);
                            }
                        });

                        builder.show();
                    }
                });


                return true;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Uri uri = data.getData();
        ImageView iv = new ImageView(OpenAlbum.this);
        iv.setImageURI(uri);
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Bitmap select = drawable.getBitmap();


        File f = new File(uri.getPath());
        Photos photos = new Photos(f);
        photos.setImage(select);
        album.addPhoto(photos);



       // albumUsers.saveToDisk(OpenAlbum.this);
        thumbAdapter = new ThumbAdapter(OpenAlbum.this, (ArrayList) album.getListofphotos());
        gridView.setAdapter(thumbAdapter);
        albumUsers.saveToDisk(OpenAlbum.this);
        Log.d("LIST OPENED",""+album.getListofphotos().size());

    }



    //on long hold 1. delete() 2.move()[DONE]
    //on click 1. open image 2. can do slideshow[DONE]
}
