package com.example.desai.app43;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SlideShow extends AppCompatActivity {

    public static final String ALBUM = "albumindec";
    public static final String PHOTO = "photoIndex";
    private int Albumrec;
    private int photorec;

    private AlbumUsers albumUsers;
    private User currentUser;
    private Album currentAlbum;
    private Photos currentPhoto;

    private ImageView imageView;
    private FloatingActionButton right;
    private FloatingActionButton left;
    private Button back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        final Bundle bundle = getIntent().getExtras();




        if(bundle!=null) {
            Albumrec = bundle.getInt(OpenPhoto.ALBUM_INDEX);
            photorec = bundle.getInt(OpenPhoto.PHOTO_INDEX);
            Log.d("ALBUMREC",Albumrec + " album");
            Log.d("ALBUMREC",photorec + " photo");

        }

        albumUsers = AlbumUsers.loadFromDisk(SlideShow.this);
        if(albumUsers.getUsers()!=null) {
            currentUser = albumUsers.getUsers().get(0);
            currentAlbum = currentUser.getAlbums().get(Albumrec);
            currentPhoto = currentAlbum.getListofphotos().get(photorec);
        }

        imageView = (ImageView) findViewById(R.id.fullImageView);
        imageView.setImageBitmap(currentPhoto.getImage());

        //getids
        right = (FloatingActionButton) findViewById(R.id.rightarrow);
        left = (FloatingActionButton) findViewById(R.id.leftarrow);
        back = (Button) findViewById(R.id.BackButton);

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Photos p =  currentAlbum.nextPhoto(currentPhoto);
               imageView.setImageBitmap(p.getImage());
               currentPhoto = p;

            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photos p = currentAlbum.prevPhoto(currentPhoto);
                currentPhoto= p;
                imageView.setImageBitmap(currentPhoto.getImage());
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle1 = new Bundle();
//                bundle.putInt(ALBUM,Albumrec);
//                bundle.putInt(PHOTO,photorec);
//
//                Intent intent = new Intent(SlideShow.this,OpenPhoto.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
                finish();
            }
        });

    }


}
