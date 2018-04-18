package com.example.desai.app43;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class searchResults extends AppCompatActivity {
private AlbumUsers albumUsers;
private User nextUser;
private Album albumtoshow;
private Button back;
private GridView gridView;
private ThumbAdapter adapter;

private  final static int USER_INDEX=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_thumbnail);

        albumUsers = AlbumUsers.loadFromDisk(this);

        Log.d("TOTALUSERS",albumUsers.getUsers().size() +"");

        nextUser = albumUsers.getUsers().get(0);
        Log.d("TOTALUSERS",nextUser.getAlbums().size() + "");

        albumtoshow = nextUser.getAlbumName("SearchR");
        Log.d("TOTALUSER4",albumtoshow.getListofphotos().size()+"");



        gridView = (GridView) findViewById(R.id.searchGrid);
        adapter = new ThumbAdapter(this, (ArrayList) albumtoshow.getListofphotos());
        gridView.setAdapter(adapter);

        back = (Button) findViewById(R.id.backSearch);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextUser.removeAlbum(albumtoshow);
                albumUsers.saveToDisk(searchResults.this);
                Intent intent = new Intent(searchResults.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
