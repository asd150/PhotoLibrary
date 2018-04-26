package com.example.desai.app43;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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
import android.widget.Toast;

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
    private Button back;
    private Button cancel;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        int index=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thumbnail);


        final Bundle bundle = getIntent().getExtras();
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

        cancel = (Button) findViewById(R.id.thumbnailCancel);
      moveButton = (Button) findViewById(R.id.moveButton);
      moveButton.setVisibility(View.INVISIBLE);
      addButton = (FloatingActionButton) findViewById(R.id.addAlbum);
      deleteButton = (FloatingActionButton) findViewById(R.id.deletePhoto);
    back = (Button) findViewById(R.id.backThumbnail);

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

                Toast.makeText(getApplicationContext(),"Selected position " + position,Toast.LENGTH_SHORT ).show();
                final int selectedPhoto = position;
                deleteButton.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                moveButton.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteButton.setVisibility(View.INVISIBLE);
                        cancel.setVisibility(View.INVISIBLE);
                        moveButton.setVisibility(View.INVISIBLE);
                        addButton.setVisibility(View.VISIBLE);
                        back.setVisibility(View.VISIBLE);

                    }
                });
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
                                back.setVisibility(View.VISIBLE);
                                deleteButton.setVisibility(View.INVISIBLE);
                                cancel.setVisibility(View.INVISIBLE);
                                moveButton.setVisibility(View.INVISIBLE);
                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                deleteButton.setVisibility(View.INVISIBLE);
                                cancel.setVisibility(View.INVISIBLE);
                                moveButton.setVisibility(View.INVISIBLE);
                                addButton.setVisibility(View.VISIBLE);
                                back.setVisibility(View.VISIBLE);
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
                                        cancel.setVisibility(View.INVISIBLE);
                                        moveButton.setVisibility(View.INVISIBLE);
                                        addButton.setVisibility(View.VISIBLE);
                                        back.setVisibility(View.VISIBLE);
                                        albumUsers.saveToDisk(OpenAlbum.this);
                                        Toast.makeText(getApplicationContext(),"Moved",Toast.LENGTH_SHORT).show();
                                    }else {
                                        deleteButton.setVisibility(View.INVISIBLE);
                                        cancel.setVisibility(View.INVISIBLE);
                                        moveButton.setVisibility(View.INVISIBLE);
                                        addButton.setVisibility(View.VISIBLE);
                                        back.setVisibility(View.VISIBLE);
                                        albumUsers.saveToDisk(OpenAlbum.this);
                                        Toast.makeText(getApplicationContext(),"Album Does Not Exist",Toast.LENGTH_SHORT).show();

                                    }


                                }
                            }

                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteButton.setVisibility(View.INVISIBLE);
                                cancel.setVisibility(View.INVISIBLE);
                                moveButton.setVisibility(View.INVISIBLE);
                                addButton.setVisibility(View.VISIBLE);
                                back.setVisibility(View.VISIBLE);
                            }
                        });

                        builder.show();
                    }
                });


                return true;
            }
        });

        //ADD BACK HERE

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenAlbum.this,MainActivity.class);
                startActivity(intent);

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {

            Uri uri = data.getData();
            ImageView iv = new ImageView(OpenAlbum.this);
            iv.setImageURI(uri);
            BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
            Bitmap select = drawable.getBitmap();


            File f = new File(uri.getPath());
            String path = f.getAbsolutePath();

            String x = null;
           // String[] proj = {MediaStore.MediaColumns.DATA};
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            if(cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME);
                x = cursor.getString(columnIndex);

            }
            if (cursor != null) cursor.close();
           // String filename = pathToFileName(path,uri);


            Photos photos = new Photos(f);
           // Log.d("PATH1",x + " name");
            //Log.d("PATH1",uri + " uri");
            //Log.d("PATH1",MediaStore.Images.Media.EXTERNAL_CONTENT_URI + " ");
            photos.setImage(select);
            photos.setCaption(x);
            album.addPhoto(photos);


            // albumUsers.saveToDisk(OpenAlbum.this);
            thumbAdapter = new ThumbAdapter(OpenAlbum.this, (ArrayList) album.getListofphotos());
            gridView.setAdapter(thumbAdapter);
            albumUsers.saveToDisk(OpenAlbum.this);
            Log.d("LIST OPENED", "" + album.getListofphotos().size());


        }
    }

//    private String pathToFileName(String pathID,Uri uri){
//
//        String id = pathID.split(":")[1];
//        String[] column = {MediaStore.Images.Media.DATA};
//        String selector = MediaStore.Images.Media._ID + "=?";
//    String filePath = null;
//        Cursor cursor = getContentResolver().query(uri,  column,
//                null, null, null);
//
//        if(cursor.moveToFirst()){
//            int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//            filePath = cursor.getString(index);
//        }
//        if (cursor != null) cursor.close();
//
//       // String filename = filePath.substring(filePath.lastIndexOf('/')+1);
//        return filePath;
//
//    }



    //on long hold 1. delete() 2.move()[DONE]
    //on click 1. open image 2. can do slideshow[DONE]
}
