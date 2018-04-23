package com.example.desai.app43;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class addTagClass extends AppCompatActivity {
    private AlbumUsers albumUsers;
    private User currentUser;
    private Album currentAlbum;
    private Photos currentPhoto;
    public static final String ALBUM_INDEX = "albumindec";
    public static final String PHOTO_INDEX = "photoIndex";
    public static final String SPINNER = "spinner";
    public static final String VALUE = "val";
    private int albumindex;
    private int photoindex;
    private EditText tagValue;
    private Spinner spinner;
    private Button saveButton;
    private Button cancelButton;
    private String tagKey,tagvalue;
    protected void onCreate( Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tag);

        tagValue  = (EditText) findViewById(R.id.tagValue);
        spinner = (Spinner) findViewById(R.id.dialog_spinner);
        saveButton = (Button) findViewById(R.id.dialogOK);
        cancelButton = (Button) findViewById(R.id.dialogCancel);
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            albumindex = bundle.getInt(OpenPhoto.ALBUM_INDEX);
            photoindex = bundle.getInt(OpenPhoto.PHOTO_INDEX);


        }
        albumUsers = AlbumUsers.loadFromDisk(addTagClass.this);
        currentUser = albumUsers.getUsers().get(0);
        currentAlbum = currentUser.getAlbums().get(albumindex);
        currentPhoto = currentAlbum.getListofphotos().get(photoindex);

        Log.d("CURRENTPHOTO1",currentPhoto.getTags().size()+"");

        Log.d("SPINNER",albumindex+"");
        Log.d("SPINNER",photoindex+"");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            add();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }

    public void add() {

        if (tagValue.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Invalid");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                }

            });
            builder.show();
        } else {


             tagKey = spinner.getSelectedItem().toString();
             tagvalue =tagValue.getText().toString().toLowerCase();
            Tag tg = new Tag(tagKey,tagvalue);
            currentPhoto.addTag(tg);
            albumUsers.saveToDisk(addTagClass.this);

            if(tagKey.equalsIgnoreCase("location")){
                currentPhoto.locationArray(tagvalue);
            }
            else if(tagKey.equalsIgnoreCase("person")){
                currentPhoto.personArray(tagKey);

            }
            else
            {}
             Bundle bundle = new Bundle();
         bundle.putInt("ALBUMINDEX", albumindex);
        bundle.putInt("PHOTOINDEX", photoindex);




        Intent intent = new Intent(addTagClass.this,OpenPhoto.class);
        intent.putExtras(bundle);
       startActivity(intent);

    }
}

    public void cancel(){

        tagValue.setText("");
        Bundle bundle = new Bundle();
        bundle.putInt(ALBUM_INDEX, albumindex);
        bundle.putInt(PHOTO_INDEX, photoindex);




        Intent intent = new Intent(addTagClass.this,OpenPhoto.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
