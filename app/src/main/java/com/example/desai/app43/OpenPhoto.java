package com.example.desai.app43;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class OpenPhoto extends AppCompatActivity {
    public static final String ALBUM_INDEX = "albumindec";
    public static final String PHOTO_INDEX = "photoIndex";
    public static final int EDIT_MOVIE_CODE=9;
private int albumindex;
private int photoIndex;
private AlbumUsers albumUsers;
private User currentUser;
private Album currentAlbum;
private Photos currentPhoto;
private FloatingActionButton add;
private ListView tagList;
private ImageView imageView;
private FloatingActionButton right;
private FloatingActionButton left;
private ArrayAdapter<Tag> adapter;
private  Button slideShow;
private Button back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideshow);

        final Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            albumindex = bundle.getInt("ALBUMINDEX");
            photoIndex = bundle.getInt("PHOTOINDEX");
        }
        albumUsers = AlbumUsers.loadFromDisk(OpenPhoto.this);
        if(albumUsers.getUsers()!=null) {
            currentUser = albumUsers.getUsers().get(0);
            currentAlbum = currentUser.getAlbums().get(albumindex);
            currentPhoto = currentAlbum.getListofphotos().get(photoIndex);
        }
        imageView = (ImageView) findViewById(R.id.fullImageView);
        imageView.setImageBitmap(currentPhoto.getImage());

        slideShow = (Button) findViewById(R.id.SlideShow);
        tagList = (ListView) findViewById(R.id.tagslistView);
        right = (FloatingActionButton) findViewById(R.id.rightarrow);
        left = (FloatingActionButton) findViewById( R.id.leftarrow);
        add = (FloatingActionButton)findViewById(R.id.addTagButton);
        back = (Button) findViewById(R.id.backSlideshow);
        tagList = (ListView) findViewById(R.id.tagslistView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,(ArrayList<Tag>)currentPhoto.getTags());
        tagList.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();

                bundle.putInt(ALBUM_INDEX,albumindex);
                bundle.putInt(PHOTO_INDEX,photoIndex);

                Intent intent = new Intent(OpenPhoto.this, addTagClass.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_MOVIE_CODE);
                //startActivity(intent);
            }
        });


        slideShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putInt(ALBUM_INDEX,albumindex);
                bundle1.putInt(PHOTO_INDEX,photoIndex);
                Log.d("ALBUMREC",albumindex + " albumI");
                Log.d("ALBUMREC",photoIndex + " photoI");

                Intent intent = new Intent(OpenPhoto.this,SlideShow.class);
                intent.putExtras(bundle1);
                startActivity(intent);
                //THIS WORKS
            }
        });



        tagList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OpenPhoto.this);

                builder.setTitle("Delete tag");
                builder.setMessage("Are you sure? You want to Delete the Tag");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int index = position;

                        currentPhoto.getTags().remove(position);
                        albumUsers.saveToDisk(OpenPhoto.this);
                        ArrayAdapter<Tag>   adapter = new ArrayAdapter<Tag>(OpenPhoto.this, android.R.layout.simple_list_item_1,(ArrayList<Tag>)currentPhoto.getTags());
                        tagList.setAdapter(adapter);
                        dialog.dismiss();

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundlex = new Bundle();
                bundlex.putInt("INDEX",albumindex);
                Log.d("ALBUMREC",albumindex + "IN back ");

                Intent intentx = new Intent(OpenPhoto.this,OpenAlbum.class);
                    intentx.putExtras(bundlex);
                    startActivity(intentx);


            }
        });

    }


//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(resultCode!=RESULT_OK){
//            Log.d("RESULT",resultCode+"");
//            return;
//        }
//        Bundle bundle = getIntent().getExtras();
//
//        if(bundle==null){
//            Log.d("RESULT","Bundel is null");
//            return;
//        }
//
//
//        int albumindexrec = bundle.getInt(addTagClass.ALBUM_INDEX);
//        int photoIndexrec = bundle.getInt(addTagClass.PHOTO_INDEX);
//        String key = bundle.getString(addTagClass.SPINNER);
//        String val = bundle.getString(addTagClass.VALUE);
//
//
//
//
//
//        albumindex = albumindexrec;
//        photoIndex = photoIndexrec;
//
//        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(OpenPhoto.this, android.R.layout.simple_list_item_1, currentPhoto.getTags());
//        tagList.setAdapter(adapter);
//       Log.d("CURRENTPHOTO",currentPhoto.getTags().size()+"");
//
//
//    }
}
